package com.example.easynotes.service;

import com.example.easynotes.dto.NoteDTO;
import com.example.easynotes.dto.NoteRequestDTO;
import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.Note;
import com.example.easynotes.model.Thank;
import com.example.easynotes.model.User;
import com.example.easynotes.repository.NoteRepository;
import com.example.easynotes.repository.ThankRepository;
import com.example.easynotes.repository.UserRepository;
import com.example.easynotes.utils.ListMapper;
import com.example.easynotes.utils.NoteTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import javax.naming.spi.ResolveResult;

import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class NoteServiceTest {
    UserRepository userRepository = Mockito.mock(UserRepository.class);

    NoteRepository noteRepository = Mockito.mock(NoteRepository.class);

    ThankRepository thankRepository = Mockito.mock(ThankRepository.class);

    ModelMapper modelMapper = new ModelMapper();

   ListMapper listMapper = new ListMapper(modelMapper);

    NoteService noteService = new NoteService(noteRepository, userRepository,thankRepository, modelMapper,listMapper);

    @Test
    void getAllNotes() {
        noteService.getAllNotes();
    }

    @Test
    void createNote() {
        when(noteRepository.save(any(Note.class))).thenReturn(new Note());
        Assertions.assertDoesNotThrow(
                () -> noteService.createNote(new NoteRequestDTO()) );
    }

    @Test
    void getNoteById() {
        Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> noteService.getNoteById(1L) );
    }

    @Test
    void updateNote() {
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> noteService.updateNote(1L, new Note()));
    }

    @Test
    void updateNoteOk() {
        when(noteRepository.findById(1L)).thenReturn(Optional.of(new Note()));
        when(noteRepository.save(any(Note.class))).thenReturn(new Note());
        Assertions.assertDoesNotThrow(
                () -> noteService.updateNote(1L, new Note()));
    }

    @Test
    void deleteNote() {
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> noteService.deleteNote(1L) );
    }

    @Test
    void addReviser() {
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> noteService.addReviser(2L, 1L) );
    }


    @Test
    void addReviserOk() {

        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        when(noteRepository.findById(2L)).thenReturn(Optional.of(new Note()));
        Assertions.assertDoesNotThrow(
                () -> noteService.addReviser(2L, 1L) );
    }

    @Test
    void getThanks() {
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> noteService.getThanks(3L) );
    }

    @Test
    void getThanksOk() {
        var pedro = new User();
        var note = new Note();
        note.setThanks(Set.of(new Thank(pedro, note)));
        when(noteRepository.findById(3L)).thenReturn(Optional.of(note));
        Assertions.assertDoesNotThrow(
                () -> noteService.getThanks(3L) );
    }


    @Test
    void getThreeMoreThankedNotes() {
        noteService.getThreeMoreThankedNotes(2020);
    }

    @Test
    void getNoteTypeofNotExistingNote()
    {
        // Arrange
        NoteTypes expected = NoteTypes.Normal;
        NoteDTO resultDTO;
        // Act
        Mockito.when(thankRepository.findThanksCountByNoteId(Mockito.anyLong())).thenReturn(0);
        resultDTO = noteService.requestNoteType(0L);
        // Assert
        Assertions.assertEquals(expected, resultDTO.getTypeNote());
    }

    @Test
    void getNoteTypeofNoteWith10Likes()
    {
        // Arrange
        NoteTypes expected = NoteTypes.DeInteres;
        NoteDTO resultDTO;
        // Act
        Mockito.when(thankRepository.findThanksCountByNoteId(Mockito.anyLong())).thenReturn(10);
        resultDTO = noteService.requestNoteType(0L);
        // Assert
        Assertions.assertEquals(expected, resultDTO.getTypeNote());
    }

    @Test
    void getNoteTypeofNoteWith11Likes()
    {
        // Arrange
        NoteTypes expected = NoteTypes.Destacada;
        NoteDTO resultDTO;
        // Act
        Mockito.when(thankRepository.findThanksCountByNoteId(Mockito.anyLong())).thenReturn(11);
        resultDTO = noteService.requestNoteType(0L);
        // Assert
        Assertions.assertEquals(expected, resultDTO.getTypeNote());
    }
}