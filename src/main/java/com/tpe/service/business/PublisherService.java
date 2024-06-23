package com.tpe.service.business;

import com.tpe.entity.concretes.business.Publisher;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.payload.helper.MethodHelper;
import com.tpe.payload.helper.PageableHelper;
import com.tpe.payload.mappers.PublisherMapper;
import com.tpe.payload.messages.ErrorMessages;
import com.tpe.payload.messages.SuccessMessages;
import com.tpe.payload.request.PublisherRequest;
import com.tpe.payload.response.PublisherResponse;
import com.tpe.payload.response.ResponseMessage;
import com.tpe.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PublisherService {

    private final PublisherRepository publisherRepository;
    private final PublisherMapper publisherMapper;
    private final PageableHelper pageableHelper;



    public ResponseMessage<PublisherResponse> createPublisher(PublisherRequest publisherRequest) {
        if (publisherRepository.existsByName(publisherRequest.getName())){
            throw new ResourceNotFoundException(String.format(ErrorMessages.PUBLISHER_EXISTS_MESSAGE, publisherRequest.getName()));
        }

        Publisher publisher = publisherMapper.mapPublisherRequestToPublisher(publisherRequest);

        Publisher createdPublisher = publisherRepository.save(publisher);
        return ResponseMessage.<PublisherResponse>builder()
                .message(SuccessMessages.PUBLISHER_CREATED)
                .httpStatus(HttpStatus.CREATED)
                .object(publisherMapper.mapPublisherToPublisherResponse(createdPublisher))
                .build();
    }

    public Optional<PublisherResponse> getPublisherById(Long id) {
        isPublisherExist(id);
        return publisherRepository.findById(id)
                .map(publisherMapper::mapPublisherToPublisherResponse);
    }


    public Page<PublisherResponse> getAllPublisher(int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page,size,sort,type);
        return publisherRepository.findAll(pageable)
                .map(publisherMapper::mapPublisherToPublisherResponse);
    }



    private Publisher isPublisherExist(Long id){
        return publisherRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_PUBLISHER_ID,id)));
    }

    public ResponseMessage deletePublisherById(Long id) {
        Publisher publisher = isPublisherExist(id);
        if (Boolean.TRUE.equals(publisher.getBuiltIn())){
            throw new ResourceNotFoundException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        }
        publisherRepository.delete(publisher);
        return ResponseMessage.<PublisherResponse>builder()
                .message(SuccessMessages.PUBLISHER_DELETE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public PublisherResponse updatePublisherById(Long id, PublisherRequest publisherRequest) {
        Publisher publisher = isPublisherExist(id);
        if (
                !(publisher.getName().equals(publisherRequest.getName())) && //farklı ise kontroku
                        (publisherRepository.existsByNameEqualsIgnoreCase(publisherRequest.getName()))){
            throw new ConflictException(String.format(ErrorMessages.PUBLISHER_ALREADY_EXIST_WITH_NAME,publisherRequest.getName()));
        }
        Publisher updatedPublisher = publisherMapper.mapPublisherRequestToUpdatePublisher(id, publisherRequest);
        // updatedPublisher.setBooks(publisher.getBooks());
        Publisher savedPublisher = publisherRepository.save(updatedPublisher);

        return publisherMapper.mapPublisherToPublisherResponse(savedPublisher);
    }
}