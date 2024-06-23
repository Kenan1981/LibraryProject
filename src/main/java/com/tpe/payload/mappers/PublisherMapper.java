package com.tpe.payload.mappers;


import com.tpe.entity.concretes.business.Publisher;
import com.tpe.payload.request.PublisherRequest;
import com.tpe.payload.response.PublisherResponse;
import lombok.Data;
import org.springframework.stereotype.Component;


@Data
@Component
public class PublisherMapper {

    // POJO --> DTO
    public PublisherResponse mapPublisherToPublisherResponse(Publisher publisher) {
        return PublisherResponse.builder()
                .id(publisher.getId())
                .name(publisher.getName())
                .builtIn(publisher.getBuiltIn())
//                .books(publisher.getBooks().stream()
//                        .map(BookMapper::)
//                        .collect(Collectors.toList()))
                .build();
    }

    // DTO --> POJO
    public Publisher mapPublisherRequestToPublisher(PublisherRequest publisherRequest) {
        return  Publisher.builder()
                .name(publisherRequest.getName())
                .builtIn(publisherRequest.getBuiltIn())
//                .books(publisherRequest.getBooks().stream()
//                        .map(BookMapper::)
//                        .collect(Collectors.toList()))
                .build();
    }

    public Publisher mapPublisherRequestToUpdatePublisher(Long id ,PublisherRequest publisherRequest) {
        return Publisher.builder()
                .id(id)
                .name(publisherRequest.getName())
                .builtIn(publisherRequest.getBuiltIn())
                // .books(publisherRequest.getBooks())
                .build();
    }

}