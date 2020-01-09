package com.msr.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Site info
 *
 * @author Measurabl
 * @since 2019-06-06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Site {

    @Id
    private int id;

    private String name;

    private String address;

    private String city;

    private String state;

    private String zipcode;

    @Transient
    private List<SiteUses> siteUses;
}

////////////////////////////////////////////////////////////
// Copyright 2018  Measurabl, Inc. All rights reserved.
////////////////////////////////////////////////////////////
