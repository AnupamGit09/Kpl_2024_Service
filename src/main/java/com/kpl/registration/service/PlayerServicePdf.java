package com.kpl.registration.service;

import com.itextpdf.text.DocumentException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface PlayerServicePdf{

    void generatePdfByClassification(HttpServletResponse response, String generue)
            throws DocumentException, IOException;

    void generateSpecificPlayerPdfForCommittee(HttpServletResponse response)
            throws DocumentException, IOException;

    void generateFinalPlayerPdf(HttpServletResponse response, String generue)
            throws DocumentException, IOException;

    void generateTeamListPdf(HttpServletResponse response, String soldTeam)
            throws DocumentException, IOException;
}
