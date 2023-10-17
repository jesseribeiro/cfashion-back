package br.com.crista.fashion.bean;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import br.com.crista.fashion.enumeration.EnumRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity ( name = "Role")
public class RoleBean extends GenericBean {

    @Enumerated(EnumType.STRING)
    private EnumRole nome;
}
