/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuatroenlinea.controlador;

import com.cuatroenlinea.controlador.util.JsfUtil;
import com.cuatroenlinea.modelo.TipoUsuario;
import com.cuatroenlinea.modelo.Usuario;
import static com.cuatroenlinea.modelo.Usuario_.tipoUsuario;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
/**
 *
 * @author USUARIO
 */
@Named(value = "loginBean")
@ViewScoped
public class LoginBean implements Serializable{
private Usuario usuario;
    @EJB   
    private UsuarioFacade usuarioFacade;
    private String tipoUsuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public UsuarioFacade getUsuarioFacade() {
        return usuarioFacade;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public void setUsuarioFacade(UsuarioFacade usuarioFacade) {
        this.usuarioFacade = usuarioFacade;
    }

    public LoginBean() {
    }
    @PostConstruct
    private void inicializar()
    {
        usuario= new Usuario();
    }
    
 
     
public String ingresar()
    {  
        Usuario usuarioEncontrado=usuarioFacade.find(usuario.getCorreo());
        if(usuarioEncontrado != null)
        {
            if(usuario.getContrasena().compareTo(usuarioEncontrado.getContrasena())==0)
            {
                if("administrador".equals(tipoUsuario)){
                    return "Ingresar";
                }
                return "Jugar";
            }
            JsfUtil.addErrorMessage("Contraseña errada");
        }
        else
        {
            JsfUtil.addErrorMessage("El correo ingresado no existe");
        }
        return null;
}
}
