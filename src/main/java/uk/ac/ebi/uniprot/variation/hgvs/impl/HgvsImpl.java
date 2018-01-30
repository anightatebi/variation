package uk.ac.ebi.uniprot.variation.hgvs.impl;

import lombok.Data;
import uk.ac.ebi.uniprot.variation.hgvs.Hgvs;
import uk.ac.ebi.uniprot.variation.hgvs.HgvsDescription;
import uk.ac.ebi.uniprot.variation.hgvs.HgvsType;

@Data
public class HgvsImpl implements Hgvs {
	private final HgvsType type;
	private final String sequenceId;
	private final HgvsDescription description;
	

}
