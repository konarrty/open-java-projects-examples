package com.example.tourmanagement.aspect;

import com.example.tourmanagement.model.entity.Hotel;
import com.example.tourmanagement.model.entity.TourDetails;
import com.example.tourmanagement.model.entity.User;
import com.example.tourmanagement.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
@Aspect
public class ImageNameFetchAspect {

    private final FileUtils fileUtils;

    @Before("(execution(public com.example.tourmanagement.dto.UserDTO toDto(com.example.tourmanagement.model.entity.User))) &&"
            + "args(user)")
    public void imageFetchToUser(User user) {

        List<String> images = fileUtils.getImageNames(
                User.class,
                user.getId());

        user.setImage(!images.isEmpty() ? images.get(0) : null);
    }

    @Before("(execution(public com.example.tourmanagement.dto.HotelDTO toDto(com.example.tourmanagement.model.entity.Hotel))) &&"
            + "args(hotel)")
    public void imageFetchToHotel(Hotel hotel) {

        hotel.setImageURL(fileUtils.getImageNames(
                Hotel.class,
                hotel.getId()));
    }


    @Before("(execution(public com.example.tourmanagement.dto.response.TourDetailsResponse toResponse(com.example.tourmanagement.model.entity.TourDetails))) &&"
            + "args(details)")
    public void imageFetchToDetail(TourDetails details) {

        details.setImages(fileUtils.getImageNames(
                TourDetails.class,
                details.getId()));
    }

    @Before("(execution(public java.util.List<com.example.tourmanagement.dto.response.TourDetailsResponse> com.example.tourmanagement.mapper.tour.TourDetailsMapper.toResponse(Iterable<com.example.tourmanagement.model.entity.TourDetails>))) &&"
            + "args(details)")
    public void imageFetchToDetails(Iterable<TourDetails> details) {

        details.forEach(this::imageFetchToDetail);
    }

    @Before("(execution(public Iterable<com.example.tourmanagement.dto.HotelDTO> toDTO(Iterable<com.example.tourmanagement.model.entity.Hotel>))) &&"
            + "args(hotels)")
    public void imageFetchToHotels(Iterable<Hotel> hotels) {

        hotels.forEach(this::imageFetchToHotel);
    }

    @Before("(execution(public Iterable<com.example.tourmanagement.dto.UserDTO> toDTO(Iterable<com.example.tourmanagement.model.entity.User>))) &&"
            + "args(users)")
    public void imageFetchToUsers(Iterable<User> users) {

        users.forEach(this::imageFetchToUser);
    }

}
