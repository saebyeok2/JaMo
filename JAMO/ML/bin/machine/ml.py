from torch import Tensor
import argparse
import torch
import torch.nn as nn
import numpy as np
import torchaudio
import librosa
import Levenshtein as Lev
from torch import Tensor
from werkzeug.utils import secure_filename

from kospeech.vocabs.ksponspeech import KsponSpeechVocabulary
from kospeech.data.audio.core import load_audio
from kospeech.models import (
    SpeechTransformer,
    Jasper,
    DeepSpeech2,
    ListenAttendSpell,
    Conformer,
)

def parse_audio(audio_path: str, del_silence: bool = False, audio_extension: str = 'wav') -> Tensor:
    signal = load_audio(audio_path, del_silence, extension=audio_extension)     # 음성파일을 numpy형태의 waveform으로 바꿔주는 함수
    print("AUDIO PARSE COMPLETE.")
    feature = torchaudio.compliance.kaldi.fbank(
        waveform=Tensor(signal).unsqueeze(0),
        num_mel_bins=80,
        frame_length=20,
        frame_shift=10,
        window_type='hamming'
    ).transpose(0, 1).numpy()

    feature -= feature.mean()
    feature /= np.std(feature)

    return torch.FloatTensor(feature).transpose(0, 1)

#출처 https://github.com/letgodchan0/Korean_STT/tree/main/koreanSTT
def revise(sentence):
    words = sentence[0].split()
    result = []
    for word in words:
        tmp = ''    
        for t in word:
            if not tmp:
                tmp += t
            elif tmp[-1]!= t:
                tmp += t
        if tmp == '스로':
            tmp = '스스로'
        result.append(tmp)
    return ' '.join(result)

def get_result(model_path: str, audio_path: str, device: str):
    
    # inference.py를 함수 내에서 매개변수로 바로 받아서 사용할 수 있게 변경
    # model_path = 학습된 모델의 경로
    # audio_path = 오디오 파일의 경로
    # device = "cpu"
    feature = parse_audio(audio_path, del_silence=True)   
    input_length = torch.LongTensor([len(feature)])
    vocab = KsponSpeechVocabulary('/home/ubuntu/22_pf041/JAMO/ML/data/vocab/aihub_character_vocabs.csv')

    model = torch.load(model_path, map_location=lambda storage, loc: storage).to(device)
    if isinstance(model, nn.DataParallel):
        model = model.module
    model.eval()

    if isinstance(model, ListenAttendSpell):
        model.encoder.device = device
        model.decoder.device = device
        
        y_hats = model.recognize(feature.unsqueeze(0), input_length)
    elif isinstance(model, DeepSpeech2):
        model.device = device
        y_hats = model.recognize(feature.unsqueeze(0), input_length)
    elif isinstance(model, SpeechTransformer) or isinstance(model, Jasper) or isinstance(model, Conformer):
        y_hats = model.recognize(feature.unsqueeze(0), input_length)

    sentence = vocab.label_to_string(y_hats.cpu().detach().numpy())
    result = revise(sentence)
    print(result)
    return result