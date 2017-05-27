package com.model;

import lombok.Getter;

public enum AnalyticalErrors {
	IZVRSEN_NALOG(1),
	NEIZVRSEN_ZBOG_PODRACUNA_KORISNIKA(2),
    NEIZVRSEN_ZBOG_RACUNA_NOSIOCA(3),
    POGRESAN_NALOG(8),
    NALOG_STOPIRAN_ZA_IZVRSENJE(9);
	
	@Getter
    private final int errorCode;

    private AnalyticalErrors(int errorCode) {
		this.errorCode = errorCode;
	}
    
}
