package com.unsis.scunsis_backend.service.sender;

import java.util.List;

import com.unsis.scunsis_backend.constants.Constant;
import com.unsis.scunsis_backend.exception.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.unsis.scunsis_backend.dto.request.sender.SenderRequest;
import com.unsis.scunsis_backend.dto.response.sender.SenderResponse;
import com.unsis.scunsis_backend.mapper.sender.SenderMapper;
import com.unsis.scunsis_backend.repository.sender.ISenderRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;
@Data
@Service
@RequiredArgsConstructor
public class SenderService {

    private final ISenderRepository senderRepository;
    private final SenderMapper senderMapper;

    public SenderResponse getById(long senderId){
        return senderRepository.findById(senderId)
                .map(senderMapper::toDto)
                .orElseThrow(() -> new AppException(Constant.NOT_FOUND_SENDER, HttpStatus.NOT_FOUND));
    }

    public List<SenderResponse> getAll(){
        return senderMapper.toDtos(senderRepository.findAll());
    }

    public void deleteById(long senderId){
        if(!senderRepository.existsById(senderId)){
            throw new AppException(Constant.NOT_FOUND_SENDER, HttpStatus.NOT_FOUND);
        }
        senderRepository.deleteById(senderId);
    }

    public void createSender(SenderRequest request){
        if(senderRepository.existsByName(request.getName().trim())){
           throw new AppException(Constant.SENDER_EXISTS, HttpStatus.CONFLICT);
        }
        senderRepository.save(senderMapper.toEntity(request));
    }
}
