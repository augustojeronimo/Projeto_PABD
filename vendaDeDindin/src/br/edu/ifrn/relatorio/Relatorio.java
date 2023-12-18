package br.edu.ifrn.relatorio;

import java.io.InputStream;
import java.util.List;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class Relatorio {
    
    //método para gerar relatório
    public void gerarRelatorio(List<DadosRelatorio> lista) throws JRException{
        
        InputStream caminho = Relatorio.class.getResourceAsStream("ModeloRelatorio.jrxml");
        
        JasperReport report = JasperCompileManager.compileReport(caminho);
        JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(lista));
        JasperViewer.viewReport(print, false);
    }
    
}
