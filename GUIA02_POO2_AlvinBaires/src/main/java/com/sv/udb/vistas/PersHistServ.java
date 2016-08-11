/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.vistas;

import com.sv.udb.controlador.*;
import com.sv.udb.modelo.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig(maxFileSize = 16177215)
@WebServlet(name = "PersHistServ", urlPatterns = {"/PersHistServ"})

public class PersHistServ extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            System.out.println("Lamen");
        boolean esValido = request.getMethod().equals("POST");
        if(esValido)
        {
            String mens = "";
            System.out.println("Laca");
            String CRUD = request.getParameter("accion");
            System.out.println("boton " +CRUD);
            if(CRUD.equals("Guardar"))
            {
                if(request.getParameter("cmbTipoPers").trim().equals("")||request.getParameter("cmbUbicGeog").trim().equals("") ||request.getParameter("nombrePersona").trim().equals("") || request.getParameter("nombrePersona").trim().equals("apellidoPersona"))
                {
                    mens="Campos vacíos";
                }
                else
                {
                    InputStream inputStream = null;
                    Part filePart;
                    try
                    {
                        filePart = request.getPart("fotoPersona");
                    }
                    catch(Exception err)
                    {
                        filePart=null;
                        System.out.println("SIN FOTO");
                    }
                    if (filePart != null) {
                        // prints out some information for debugging
                        if(filePart.getContentType().equals("image/jpg")||filePart.getContentType().equals("image/gif")||filePart.getContentType().equals("image/jpeg")||filePart.getContentType().equals("image/png"))
                        {
                            System.out.println(filePart.getName());
                            System.out.println(filePart.getSize());
                            System.out.println(filePart.getContentType());
                            // obtains input stream of the upload file
                            inputStream = filePart.getInputStream();
                            
                            Pers objePers = new Pers();
                            objePers.setNomb_pers(request.getParameter("nombrePersona"));
                            objePers.setApel_pers(request.getParameter("apellidoPersona"));
                            objePers.setCodi_tipo_pers(new TipoPers(Integer.parseInt(request.getParameter("cmbTipoPers")),null,null,null,null));
                            objePers.setCodi_ubic_geog(new UbicGeog(Integer.parseInt(request.getParameter("cmbUbicGeog")),null,0,null,null,null));
                            
                            mens = new PersCtrl().guardar(objePers,inputStream)? "Datos Guardados" : "Datos no guardados";
                            
                            
                            /*Cliente cli = new Cliente();
                            Institucion ins = new Institucion();
                            ins.setCodigoInstitucion(Integer.parseInt(request.getParameter("cmbInsti")));
                            cli.setObjetoInstitucion(ins);
                            cli.setNombreCliente(request.getParameter("nombreCliente"));
                            cli.setTelefonoCliente(request.getParameter("telefonoCliente"));
                            mens = new ClienteCtrl().guardar(cli) ? "Datos guardados" : "Datos no guardados";*/
                        }
                        else
                        {
                            mens="Formato de imagen incorrecto";
                        }                        
                    }
                }
                
                
            }
            else if(CRUD.equals("Modificar"))
            {
                /*
                Cliente cli = new Cliente();
                Institucion ins = new Institucion();
                cli.setCodigoCliente(Integer.parseInt(request.getParameter("codigoCliente")));
                ins.setCodigoInstitucion(Integer.parseInt(request.getParameter("cmbInsti")));
                cli.setObjetoInstitucion(ins);
                cli.setNombreCliente(request.getParameter("nombreCliente"));
                cli.setTelefonoCliente(request.getParameter("telefonoCliente"));
                if(cli != null)
                {
                    mens = new ClienteCtrl().modificar(cli) ? "Datos modificados" : "Datos no modificados";
                }
                */
            }
            else if(CRUD.equals("Eliminar"))
            {
                /*
                Cliente cli = new Cliente();
                cli.setCodigoCliente(Integer.parseInt(request.getParameter("codigoCliente")));
                mens = new ClienteCtrl().eliminar(cli) ? "Datos eliminados" : "Datos no eliminados";
                */
            }
            else if(CRUD.equals("Consultar"))
            {
                /*
                int codicli = Integer.parseInt(request.getParameter("codiCliRadi") == null ? "0" : request.getParameter("codiCliRadi"));
                Cliente objecli = new ClienteCtrl().cons(codicli);
                if(objecli != null)
                {
                    request.setAttribute("codigoCliente", codicli);
                    request.setAttribute("cmbInsti", objecli.getObjetoInstitucion().getCodigoInstitucion());
                    request.setAttribute("nombreCliente", objecli.getNombreCliente());
                    request.setAttribute("telefonoCliente", objecli.getTelefonoCliente()); 
                } 
                */
            }
            request.setAttribute("mensAler", mens);
            request.getRequestDispatcher("/Personas.jsp").forward(request, response);
        }
        else
        {
            response.sendRedirect(request.getContextPath() + "/Personas.jsp");
        }
    }
 }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
