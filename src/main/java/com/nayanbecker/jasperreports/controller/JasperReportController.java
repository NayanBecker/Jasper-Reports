package com.nayanbecker.jasperreports.controller;

import java.io.FileNotFoundException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nayanbecker.jasperreports.model.Aluno;
import com.nayanbecker.jasperreports.service.JasperResportService;

@RestController
@RequestMapping("/jasper-report")
public class JasperReportController {
    
    private final JasperResportService jasperResportService;

    public JasperReportController(JasperResportService jasperResportService){
        this.jasperResportService = jasperResportService;
    }

    @PostMapping("/gerar-certificado")
    public void gerar(@RequestBody Aluno aluno) throws FileNotFoundException {
        this.jasperResportService.gerar(aluno);
    }
}
