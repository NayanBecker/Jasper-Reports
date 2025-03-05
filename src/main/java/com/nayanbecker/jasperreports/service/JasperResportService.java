package com.nayanbecker.jasperreports.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.nayanbecker.jasperreports.model.Aluno;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
public class JasperResportService {

    public static final String CERTIFICADOS = "classpath:jasper/certificados/";

    public static final String IMAGEBG = "classpath:jasper/img/jasper-img.jpg";

    public static final String ARQUIVOXJRXML = "certificadoteste.jrxml";

    public static final Logger LOGGER = LoggerFactory.getLogger(JasperResportService.class);

    public static final String DESTINOPDF = "C:/Users/nayan.becker/jasper-report/";

    public void gerar(Aluno aluno) throws IOException {

        byte[] imagebg = this.loadimage(IMAGEBG);

        Map<String, Object> params = new HashMap<>();

        params.put("nome", aluno.getNome());
        params.put("curso", aluno.getCurso());
        params.put("cargaHoraria", aluno.getCargaHoraria());
        params.put("dataInicioCurso", aluno.getDataInicioCurso());
        params.put("dataTerminoCurso", aluno.getDataTerminoCurso());
        params.put("imageJasper", imagebg);

        String pathAbsolute = getAbsolutePath();

        try {
            String folderDiretorio = getDiretorioSave("certificados-salvos");
            JasperReport report = JasperCompileManager.compileReport(pathAbsolute);
            LOGGER.info("report Compilado: {} ", pathAbsolute);
            JasperPrint print = JasperFillManager.fillReport(report, params, new JREmptyDataSource());
            LOGGER.info("Jasper print");
            JasperExportManager.exportReportToPdfFile(print, folderDiretorio);
            LOGGER.info("PDF EXPORTADO PARA {} ", folderDiretorio);

        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] loadimage(String imagebg) throws IOException {
        String image = ResourceUtils.getFile(imagebg).getAbsolutePath();
        File file = new File(image);
        try (InputStream inputStream = new FileInputStream(file)) {
            return IOUtils.toByteArray(inputStream);
        }
    }

    private String getDiretorioSave(String name) {
        this.createDiretorio(DESTINOPDF);
        return DESTINOPDF + name.concat(".pdf");
    }

    private void createDiretorio(String name) {
        File dir = new File(name);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    private String getAbsolutePath() throws FileNotFoundException {
        return ResourceUtils.getFile(CERTIFICADOS + ARQUIVOXJRXML).getAbsolutePath();

    }
}
