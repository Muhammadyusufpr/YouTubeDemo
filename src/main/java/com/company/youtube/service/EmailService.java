package com.company.youtube.service;

import com.company.youtube.dto.EmailDTO;
import com.company.youtube.enams.EmailType;
import com.company.youtube.entity.EmailEntity;
import com.company.youtube.exception.ItemNotFoundException;
import com.company.youtube.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private EmailRepository emailRepository;

    public void send(String toEmail, String title, String content) {
        SimpleMailMessage simple = new SimpleMailMessage();
        simple.setTo(toEmail);
        simple.setSubject(title);
        simple.setText(content);
        javaMailSender.send(simple);

        EmailEntity entity = new EmailEntity();
        entity.setToEmail(toEmail);
        entity.setType(EmailType.VERIFICATION);
        emailRepository.save(entity);
    }

    public List<EmailDTO> paginationList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "sendDate"));

        List<EmailDTO> dtoList = new ArrayList<>();
        emailRepository.findAll(pageable).stream().forEach(entity -> {
            dtoList.add(toDTO(entity));
        });

        return dtoList;
    }

    public Boolean delete(String id) {
        emailRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Not found!"));

        emailRepository.deleteById(id);
        return true;
    }

    private EmailDTO toDTO(EmailEntity entity) {
        EmailDTO dto = new EmailDTO();
        dto.setId(entity.getId());
        dto.setToEmail(entity.getToEmail());
        dto.setType(entity.getType());
        dto.setSendDate(entity.getCreatedDate());
        return dto;
    }
}
