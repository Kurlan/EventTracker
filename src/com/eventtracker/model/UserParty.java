package com.eventtracker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_party")
public class UserParty {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "user_id")
    private String userId;
    
    @Column(name = "party_id")
    private String partyId;
    
    @Column(name = "type")
    private String type;

}
