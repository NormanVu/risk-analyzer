package com.scirisk.riskanalyzer.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.scirisk.riskanalyzer.domain.Network;
import com.scirisk.riskanalyzer.model.NetworkParser;
import com.scirisk.riskanalyzer.model.NetworkParserDomImpl;
import com.scirisk.riskanalyzer.model.NetworkValidationException;
import com.scirisk.riskanalyzer.persistence.NetworkManager;
import com.scirisk.riskanalyzer.persistence.jpa.NetworkManagerJpaImpl;

@SuppressWarnings("serial")
public class ImportServlet extends HttpServlet {

  /** Logger for this class. */
  private static Logger log = Logger.getLogger(ImportServlet.class.getName());

  private NetworkManager manager;

  public void init(final ServletConfig config) throws ServletException {
    this.manager = new NetworkManagerJpaImpl();
  };

  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    log.fine("Importing network from XML file..");
    resp.setContentType("text/html");
    PrintWriter out = resp.getWriter();

    boolean isMulitpart = ServletFileUpload.isMultipartContent(req);
    if (isMulitpart) {

      ServletFileUpload uploadHandler = new ServletFileUpload();
      try {
        FileItemIterator iterator = uploadHandler.getItemIterator(req);
        while (iterator.hasNext()) {
          FileItemStream fis = iterator.next();
          if (!fis.isFormField()) {
            InputStream is = fis.openStream();
            NetworkParser networkParser = new NetworkParserDomImpl();
            Network network = networkParser.parse(is);
            log.fine("Network has been parsed: " + network);

            manager.save(network);
            break; // stop iterating
          }
        }
        out.println("{success: true}");
      } catch (FileUploadException e) {
        e.printStackTrace();
        out.println("{success: false}");
      } catch (NetworkValidationException e) {
        e.printStackTrace();
        out.println("{success: false}");
      }
    } else {
      out.println("{success: false}");
    }
  }

}