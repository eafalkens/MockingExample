package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingSystemTest {

    @Mock
    TimeProvider timeProvider;

    @Mock
    RoomRepository roomRepository;

    @Mock
    NotificationService notificationService;

    @InjectMocks
    BookingSystem bookingSystem;

    @ParameterizedTest
    @MethodSource ("nullParameters")
    void bookRoomShouldThrowExceptionWhenAnyRequiredParameterIsNull(
        String roomId,
        LocalDateTime startTime,
        LocalDateTime endTime
    ) {
        assertThatThrownBy(() ->
                bookingSystem.bookRoom(roomId, startTime, endTime)
        ).isInstanceOf(IllegalArgumentException.class);
    }
    static Stream<Arguments> nullParameters() {
        LocalDateTime now = LocalDateTime.of(2026, 1, 1, 0, 0);
        return Stream.of(
                Arguments.of(null, now, now.plusHours(1)),
                Arguments.of("1", null, now.plusHours(1)),
                Arguments.of("1", now, null)
        );
    }

    @Test
    void bookRoomShouldThrowExceptionWhenStartTimeIsInThePast() {
        String roomId = "1";
        LocalDateTime now = LocalDateTime.of(2026, 1, 1, 0, 0);
        LocalDateTime startTime = now.minusHours(1);
        LocalDateTime endTime = now.plusHours(1);

        when(timeProvider.getCurrentTime()).thenReturn(now);

        assertThatThrownBy(() ->
                bookingSystem.bookRoom(roomId, startTime, endTime)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void bookRoomShouldThrowExceptionWhenEndTimeIsBeforeStartTime() {
        String roomId = "1";
        LocalDateTime now = LocalDateTime.of(2026, 1, 1, 0, 0);
        LocalDateTime startTime = now.plusHours(1);
        LocalDateTime endTime = startTime.plusHours(1);

        when(timeProvider.getCurrentTime()).thenReturn(now);

        assertThatThrownBy(() ->
                bookingSystem.bookRoom(roomId, startTime, endTime)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void bookRoomShouldThrowExceptionWhenRoomDoesNotExist() {
        String roomId = "1";
        LocalDateTime now = LocalDateTime.of(2026, 1, 1, 0, 0);
        LocalDateTime startTime = now.plusHours(1);
        LocalDateTime endTime = startTime.plusHours(1);

        when(timeProvider.getCurrentTime()).thenReturn(now);
        when(roomRepository.findById(roomId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                bookingSystem.bookRoom(roomId, startTime, endTime)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void bookRoomShouldReturnFalseWhenRoomIsNotAvailable() {
        String roomId = "1";
        LocalDateTime now = LocalDateTime.of(2026, 1, 1, 0, 0);
        LocalDateTime startTime = now.plusHours(1);
        LocalDateTime endTime = startTime.plusHours(1);

        when(timeProvider.getCurrentTime()).thenReturn(now);
        Room room = new Room("1", "Room A");
        Booking booking = new Booking("2", roomId, startTime, endTime);
        room.addBooking(booking);
        when(roomRepository.findById(roomId))
                .thenReturn(Optional.of(room));

        boolean result = bookingSystem.bookRoom(roomId, startTime, endTime);

        assertThat(result).isFalse();
    }

    @Test
    void bookRoomShouldReturnTrueWhenBookingIsSuccessful() {
        String roomId = "1";
        LocalDateTime now = LocalDateTime.of(2026, 1, 1, 0, 0);
        LocalDateTime startTime = now.plusHours(1);
        LocalDateTime endTime = startTime.plusHours(1);

        when(timeProvider.getCurrentTime()).thenReturn(now);
        Room room = new Room("1", "Room A");
        when(roomRepository.findById(roomId))
                .thenReturn(Optional.of(room));

        boolean result = bookingSystem.bookRoom(roomId, startTime, endTime);

        assertThat(result).isTrue();
    }

    @Test
    void bookRoomShouldReturnTrueEvenIfNotificationFails() throws NotificationException {
        String roomId = "1";
        LocalDateTime now = LocalDateTime.of(2026, 1, 1, 0, 0);
        LocalDateTime startTime = now.plusHours(1);
        LocalDateTime endTime = startTime.plusHours(1);

        when(timeProvider.getCurrentTime()).thenReturn(now);

        Room room = new Room("1", "Room A");
        when(roomRepository.findById(roomId))
                .thenReturn(Optional.of(room));

        doThrow(new NotificationException("Failed"))
                .when(notificationService)
                .sendBookingConfirmation(any(Booking.class));

        boolean result = bookingSystem.bookRoom(roomId, startTime, endTime);

        assertThat(result).isTrue();
    }
}
