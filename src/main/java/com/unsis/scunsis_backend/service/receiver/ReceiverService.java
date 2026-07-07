package com.unsis.scunsis_backend.service.receiver;

import java.util.List;

import com.unsis.scunsis_backend.constants.Constant;
import com.unsis.scunsis_backend.exception.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.unsis.scunsis_backend.dto.request.receiver.ReceiverRequest;
import com.unsis.scunsis_backend.dto.response.receiver.ReceiverResponse;
import com.unsis.scunsis_backend.mapper.receiver.ReceiverMapper;
import com.unsis.scunsis_backend.model.receiver.Receiver;
import com.unsis.scunsis_backend.repository.receiver.IReceiverRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;
@Data
@Service
@RequiredArgsConstructor
public class ReceiverService {

    private final IReceiverRepository receiverRepository;
    private final ReceiverMapper receiverMapper;

    public ReceiverResponse getById(long receiverId){
        return receiverRepository.findById(receiverId)
                .map(receiverMapper::toDto)
                .orElseThrow(() -> new AppException(Constant.NOT_FOUND_BY_ID, HttpStatus.NOT_FOUND));
    }

    public List<ReceiverResponse> getAll(){
        List<Receiver> receivers = receiverRepository.findAll();
        List<ReceiverResponse> receiversResponse = receiverMapper.toDtos(receivers);
        return receiversResponse;
    }

    public void deleteById(long receiverId){
        if(!receiverRepository.existsById(receiverId)){
            throw new AppException(Constant.NOT_FOUND_BY_ID, HttpStatus.NOT_FOUND);
        }
        receiverRepository.deleteById(receiverId);
    }

    public void createReceiver(ReceiverRequest request){
        if(receiverRepository.existsByEmail(request.getEmail().trim())){
            throw new AppException(Constant.MAIL_FOUND, HttpStatus.CONFLICT);
        }
        receiverRepository.save(receiverMapper.toEntity(request));
    }

}
