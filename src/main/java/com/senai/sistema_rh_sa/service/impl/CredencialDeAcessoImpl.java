package com.senai.sistema_rh_sa.service.impl;

import com.google.common.base.Preconditions;
import com.senai.sistema_rh_sa.entity.Usuario;
import com.senai.sistema_rh_sa.security.CredencialDeAcesso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CredencialDeAcessoImpl implements UserDetailsService{

	@Value("login.usuario.salvo")
	private String loginSalvo;

	@Value("senha.usuario.salvo")
	private String senhaSalva;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

		Preconditions.checkArgument(login.equals(loginSalvo),
				"O login está inválido");
		Usuario usuario = new Usuario(login, senhaSalva);
		return new CredencialDeAcesso(usuario);
	}
}
