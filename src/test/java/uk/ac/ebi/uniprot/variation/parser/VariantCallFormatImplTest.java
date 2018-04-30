package uk.ac.ebi.uniprot.variation.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import uk.ac.ebi.uniprot.variation.VariantCallFormat;
import uk.ac.ebi.uniprot.variation.impl.VariantCallFormatImpl;

public class VariantCallFormatImplTest {

    @Test
    public void testVariantCallFormatImpl() {
        VariantCallFormatImpl.Builder builder= VariantCallFormatImpl.builder();
        builder.chromosome("12");
        VariantCallFormat variantCallFormat = builder.build();
        assertEquals("12", variantCallFormat.getChromosome());
    }

}
