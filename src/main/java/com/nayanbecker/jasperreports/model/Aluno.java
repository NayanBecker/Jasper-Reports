package com.nayanbecker.jasperreports.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Aluno {
    
    private String nome;
    private String curso;
    private Integer cargaHoraria;
    private Date dataInicioCurso;
    private Date dataTerminoCurso;

}
