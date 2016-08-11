/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controlador;

import com.sv.udb.modelo.*;
import com.sv.udb.recursos.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersHistCtrl {
    public List<PersHist> consTodo()
    {
        List<PersHist> resp = new ArrayList<>();
        Connection cn = new Conexion().getConn();
        try
        {
            String consulta = "select * from pers_hist, pers, tipo_pers, ubic_geog where pers.CODI_PERS = pers_hist.CODI_PERS and tipo_pers.CODI_TIPO_PERS = pers_hist.CODI_TIPO_PERS and ubic_geog.CODI_UBIC_GEOG = pers_hist.CODI_UBIC_GEOG and pers_hist.CODI_PERS_HIST=(select max(p.CODI_PERS_HIST) from pers_hist p where p.CODI_PERS = pers.CODI_PERS)";
            PreparedStatement cmd = cn.prepareStatement(consulta);
            ResultSet rs = cmd.executeQuery();
            while(rs.next())
            {
                PersHist objePersHist = new PersHist();
                Pers objePers = new Pers();
                TipoPers objeTipoPers = new TipoPers();
                UbicGeog objeUbicGeog = new UbicGeog();
                
                objePersHist.setCodi_pres_hist(rs.getInt("pers_hist.codi_pers_hist"));
                objePers.setCodi_pers(rs.getInt("pers.codi_Pers"));
                objePersHist.setNomb_pers(rs.getString("pers_hist.nomb_pers_hist"));
                objePersHist.setApel_pers(rs.getString("pers_hist.apel_pers_hist"));
                objePersHist.setFoto_pers(rs.getBlob("pers_hist.foto_pers"));
                
                objeTipoPers.setCodi_tipo_pers(rs.getInt("tipo_pers.codi_tipo_pers"));
                objeTipoPers.setNomb_tipo_pers("tipo_pers.nomb_tipo_pers");
                objePersHist.setCodi_tipo_pers(objeTipoPers);
                
                objeUbicGeog.setCODI_UBIC_GEOG(rs.getInt("ubic_geog.codi_ubic_geog"));
                objeUbicGeog.setNOMB_UBIC_GEOG(rs.getString("ubic_geog.nomb_ubic_geog"));
                objeUbicGeog.setCODI_UBIC_GEOG_SUPE(rs.getInt("ubic_geog.codi_ubic_geog_supe"));
                objePersHist.setCodi_ubic_geog(objeUbicGeog);
                
                objePersHist.setFech_alta("pers_hist.fech_alta");
                objePersHist.setFech_baja("pers_hist.fech_baja");
                objePersHist.setEsta(rs.getInt("pers_hist.esta"));
                
                resp.add(objePersHist);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if(cn != null)
            {
                try
                {
                    if(!cn.isClosed())
                    {
                        cn.close();
                    }
                }
                catch(SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        }
        return resp;
    }
}
