package com.nayanbecker.jasperreports.service;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.nayanbecker.jasperreports.model.Aluno;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
public class JasperResportService {

    public static final String CERTIFICADOS = "classpath:jasper/cetificados";

    public static final String ARQUIVOXJRXML = "certificadoteste.jrxml";

    public static final Logger LOGGER = LoggerFactory.getLogger(JasperResportService.class);

    public static final String DESTINOPDF = "C:\\jasper-report";

    public void gerar(Aluno aluno) throws FileNotFoundException{
        
        Map<String, Object> params = new HashMap<>();

        params.put("nome", aluno.getNome());
        params.put("curso", aluno.getCurso());
        params.put("cargaHoraria", aluno.getCargaHoraria());
        params.put("dataInicioCurso", aluno.getDataInicioCurso());
        params.put("dataTerminoCurso", aluno.getDataTerminoCurso());

        String pathAbsolute = getAbsolutePath();
        
        
                try{
                    JasperReport report = JasperCompileManager.compileReport(pathAbsolute);
                    LOGGER.info("report Compilado: {} ", pathAbsolute);
                    JasperPrint print = JasperFillManager.fillReport(report, params, new JREmptyDataSource());
                    LOGGER.info("Jasper print");

                } catch(JRException e){
                    throw new RuntimeException(e);
                }
            }

    private String getAbsolutePath() throws FileNotFoundException {
        return ResourceUtils.getFile(CERTIFICADOS + ARQUIVOXJRXML).getAbsolutePath();

            }
}
