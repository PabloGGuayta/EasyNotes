package com.example.easynotes.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ThankRepositoryTest
{
    @Autowired
    ThankRepository thankRepository;

    @Test
    void findThanksCountForNotExistingNoteId()
    {
        // Arrange
        int expected = 0;
        int result = 0;
        // Act
        result = thankRepository.findThanksCountByNoteId(10000L);
        // Assert
        Assertions.assertEquals(expected, result);
    }

    @Test
    void findThanksCountForNoteIdWith6Thanks()
    {
        // Arrange
        int expected = 6;
        int result = 0;
        // Act
        result = thankRepository.findThanksCountByNoteId(1L);
        // Assert
        Assertions.assertEquals(expected, result);
    }

    @Test
    void findThanksCountForNoteIdWithoutThanks()
    {
        // Arrange
        int expected = 0;
        int result = 0;
        // Act
        result = thankRepository.findThanksCountByNoteId(40L);
        // Assert
        Assertions.assertEquals(expected, result);
    }

}