package com.gabru.Patrimonio.domain.services.LecturaArchivos;

import java.util.function.Supplier;
public enum LectorTipo {
    CSV(   () -> new StrategyCsv()),
    EXCEL( () -> new StrategyCsv()), //Todo Pending Implementation
    XML(   () -> new StrategyCsv()); //Todo Pending Implementation
    private final Supplier<LectorArchivosStrategy> figureCreator;
    LectorTipo(Supplier<LectorArchivosStrategy> figureCreator) {
        this.figureCreator = figureCreator;
    }
    public LectorArchivosStrategy create () {
        return this.figureCreator.get();
    }

}
