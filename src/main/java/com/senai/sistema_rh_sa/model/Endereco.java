package com.senai.sistema_rh_sa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Embeddable
public class Endereco {

    @Size(max = 200, message = "O logradouro da cidade não deve conatar com mais de 200 caracteres")
    @NotBlank(message = "O logradouro é obrigatória")
    @Column(name = "logradouro")
    private String logradouro;

    @Size(max = 50, message = "O bairro da cidade não deve conatar com mais de 50 caracteres")
    @NotBlank(message = "O bairro é obrigatória")
    @Column(name = "bairro")
    private String bairro;

    @Size(max = 80, message = "O nome da cidade não deve conatar com mais de 80 caracteres")
    @NotBlank(message = "A cidade é obrigatória")
    @Column(name = "cidade")
    private String cidade;

    @Size(max = 80, message = "O nome do estado não deve conatar com mais de 80 caracteres")
    @NotBlank(message = "O estado é obrigatório")
    @Column(name = "estado")
    private String estado;
}
