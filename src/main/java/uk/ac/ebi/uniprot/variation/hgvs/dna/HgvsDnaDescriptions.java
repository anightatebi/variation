package uk.ac.ebi.uniprot.variation.hgvs.dna;

import java.util.AbstractMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.ac.ebi.uniprot.variation.exception.InvalidadHgvsDescription;
import uk.ac.ebi.uniprot.variation.hgvs.HgvsDescription;
import uk.ac.ebi.uniprot.variation.hgvs.HgvsType;
import uk.ac.ebi.uniprot.variation.hgvs.impl.HgvsDescriptionImpl;

public class HgvsDnaDescriptions {
	public final static String HGVS_SUBSTITUTION = "(\\()?(\\d+)([a-zA-Z]+)(>)([a-zA-Z]+)(\\))?";

	public final static Pattern HGVS_SUBSTITUTION_PATTERN = Pattern.compile(HGVS_SUBSTITUTION);

	// public final static String HGVS_DELETION ="(\\d+)(_\\d+)?((\\+)(\\d+))?del";
	public final static String HGVS_DELETION = "(\\d+)(([\\-\\+])(\\d+))?((_)(\\d+))?(([\\-\\+])(\\d+))?del";
	public final static Pattern HGVS_DELETION_PATTERN = Pattern.compile(HGVS_DELETION);

	public final static String HGVS_DELETION_BASE = "([\\d\\-\\+\\_\\(\\)]+)del";
	public final static Pattern HGVS_DELETION_BASE_PATTERN = Pattern.compile(HGVS_DELETION_BASE);

	public final static String HGVS_DUPLICATION_BASE = "([\\d\\-\\+\\_\\(\\)]+)dup";
	public final static Pattern HGVS_DUPLICATION_BASE_PATTERN = Pattern.compile(HGVS_DUPLICATION_BASE);

	public final static String HGVS_DUPLICATION = "(\\d+)(([\\-\\+])(\\d+))?((_)(\\d+))?(([\\-\\+])(\\d+))?dup";;
	public final static Pattern HGVS_DUPLICATION_PATTERN = Pattern.compile(HGVS_DUPLICATION);

	public final static String HGVS_INSERTION_BASE = "(.+)ins(.+)";
	public final static Pattern HGVS_INSERTION_BASE_PATTERN = Pattern.compile(HGVS_INSERTION_BASE);

	public final static String HGVS_INSERTION = "(\\d+)(_)(\\d+)ins([a-zA-Z]+)";
	public final static Pattern HGVS_INSERTION_PATTERN = Pattern.compile(HGVS_INSERTION);

	public final static String HGVS_INVERSION_BASE = "(.+)inv";
	public final static Pattern HGVS_INVERSION_BASE_PATTERN = Pattern.compile(HGVS_INVERSION_BASE);

	public final static String HGVS_INVERSION = "(\\d+)(_)(\\d+)inv";
	public final static Pattern HGVS_INVERSION_PATTERN = Pattern.compile(HGVS_INVERSION);

	public final static String HGVS_INSERTION_INVERSION_BASE = "(.+)ins(.+)inv";
	public final static Pattern HGVS_INSERTION_INVERSION_BASE_PATTERN = Pattern.compile(HGVS_INSERTION_INVERSION_BASE);

	public final static String HGVS_INSERTION_INVERSION = "(\\d+)(_)(\\d+)ins(\\d+)(_)(\\d+)inv";
	public final static Pattern HGVS_INSERTION_INVERSION_PATTERN = Pattern.compile(HGVS_INSERTION_INVERSION);

	public final static String HGVS_CONVERSION_BASE = "(.+)con(.+)";
	public final static Pattern HGVS_CONVERSION_BASE_PATTERN = Pattern.compile(HGVS_CONVERSION_BASE);

	public final static String HGVS_CONVERSION = "(\\d+)(_)(\\d+)con((.+)(:))?(\\d+)(_)(\\d+)";
	public final static Pattern HGVS_CONVERSION_PATTERN = Pattern.compile(HGVS_CONVERSION);

	public final static String HGVS_DELETION_INSERTION_BASE = "(.+)delins(.+)";
	public final static Pattern HGVS_DELETION_INSERTION_BASE_PATTERN = Pattern.compile(HGVS_DELETION_INSERTION_BASE);

	public final static String HGVS_DELETION_INSERTION = "(\\d+)((_)(\\d+))?delins([a-zA-Z]+)";
	public final static Pattern HGVS_DELETION_INSERTION_PATTERN = Pattern.compile(HGVS_DELETION_INSERTION);

	public final static String HGVS_REPEAT_BASE = "(.+)(\\[)(.+)(\\])";
	public final static Pattern HGVS_REPEAT_BASE_PATTERN = Pattern.compile(HGVS_REPEAT_BASE);

	public final static String HGVS_REPEAT = "(\\d+)((_)(\\d+))?([a-zA-Z]+)(\\[)(\\d+)(\\])(.+)?";
	public final static Pattern HGVS_REPEAT_PATTERN = Pattern.compile(HGVS_REPEAT);

	public final static String REPEAT = "([a-zA-Z]+)(\\[)(\\d+)(\\])(.+)?";
	public final static Pattern REPEAT_PATTERN = Pattern.compile(REPEAT);

	public static HgvsDescription parseHgvsDescription(String val) {

		Matcher matcher = HgvsDnaDescriptions.HGVS_DELETION_INSERTION_BASE_PATTERN.matcher(val);
		if (matcher.matches()) {
			return parseDeletionInsertionDescription(val);
		}
		matcher = HgvsDnaDescriptions.HGVS_CONVERSION_BASE_PATTERN.matcher(val);
		if (matcher.matches()) {
			return parseConversionDescription(val);
		}
		matcher = HgvsDnaDescriptions.HGVS_INSERTION_INVERSION_BASE_PATTERN.matcher(val);
		if (matcher.matches()) {
			return parseInsertionInversionDescription(val);
		}

		matcher = HgvsDnaDescriptions.HGVS_INVERSION_BASE_PATTERN.matcher(val);
		if (matcher.matches()) {
			return parseInversionDescription(val);
		}
		matcher = HgvsDnaDescriptions.HGVS_INSERTION_BASE_PATTERN.matcher(val);
		if (matcher.matches()) {
			return parseInsertionDescription(val);
		}

		matcher = HgvsDnaDescriptions.HGVS_DUPLICATION_BASE_PATTERN.matcher(val);
		if (matcher.matches()) {
			return parseDuplicationDescription(val);
		}

		matcher = HgvsDnaDescriptions.HGVS_DELETION_BASE_PATTERN.matcher(val);
		if (matcher.matches()) {
			return parseDeletionDescription(val);
		}

		matcher = HgvsDnaDescriptions.HGVS_REPEAT_BASE_PATTERN.matcher(val);
		if (matcher.matches()) {
			return parseRepeatDescription(val);
		}
		return parseSubstitutionDescription(val);
	}

	public static HgvsDescription parseSubstitutionDescription(String val) {
		Matcher matcher = HgvsDnaDescriptions.HGVS_SUBSTITUTION_PATTERN.matcher(val);
		if (matcher.matches()) {

			HgvsDescriptionImpl.HgvsDescriptionBuilder builder = HgvsDescriptionImpl.builder();
			builder.predicted(matcher.group(1) != null).start(Long.parseLong(matcher.group(2)))
					.wildType(matcher.group(3)).varType(matcher.group(5)).type(HgvsType.SUBSTITUTION).description(val)
					.parsed(true);
			return builder.build();
		} else {
			throw new InvalidadHgvsDescription(val + " does not match hgvs description type: " + HgvsType.SUBSTITUTION);
		}
	}

	public static HgvsDescription parseDeletionDescription(String val) {
		Matcher matcher = HgvsDnaDescriptions.HGVS_DELETION_PATTERN.matcher(val);
		if (matcher.matches()) {
			HgvsDescriptionImpl.HgvsDescriptionBuilder builder = HgvsDescriptionImpl.builder();
			builder.description(val).type(HgvsType.DELETION).start(Long.parseLong(matcher.group(1))).parsed(true);
			if (matcher.group(4) != null) {
				builder.startCross(Long.parseLong(matcher.group(4)));
			}

			if (matcher.group(7) != null) {
				builder.end(Long.parseLong(matcher.group(7)));
			}
			if (matcher.group(10) != null) {
				builder.endCross(Long.parseLong(matcher.group(10)));
			}
			return builder.build();
		} else {
			return parsDescriptionBase(val, HgvsDnaDescriptions.HGVS_DELETION_BASE_PATTERN, HgvsType.DELETION);
		}
	}

	public static HgvsDescription parseDuplicationDescription(String val) {
		Matcher matcher = HgvsDnaDescriptions.HGVS_DUPLICATION_PATTERN.matcher(val);
		if (matcher.matches()) {
			HgvsDescriptionImpl.HgvsDescriptionBuilder builder = HgvsDescriptionImpl.builder();
			builder.description(val).type(HgvsType.DUPLICATION).start(Long.parseLong(matcher.group(1))).parsed(true);
			if (matcher.group(4) != null) {
				builder.startCross(Long.parseLong(matcher.group(4)));
			}

			if (matcher.group(7) != null) {
				builder.end(Long.parseLong(matcher.group(7)));
			}
			if (matcher.group(10) != null) {
				builder.endCross(Long.parseLong(matcher.group(10)));
			}
			return builder.build();
		} else {
			return parsDescriptionBase(val, HgvsDnaDescriptions.HGVS_DUPLICATION_BASE_PATTERN, HgvsType.DUPLICATION);

		}
	}

	public static HgvsDescription parseInsertionDescription(String val) {
		Matcher matcher = HgvsDnaDescriptions.HGVS_INSERTION_PATTERN.matcher(val);
		if (matcher.matches()) {
			HgvsDescriptionImpl.HgvsDescriptionBuilder builder = HgvsDescriptionImpl.builder();
			builder.description(val).type(HgvsType.INSERTION).start(Long.parseLong(matcher.group(1))).parsed(true)
					.end(Long.parseLong(matcher.group(3))).varType(matcher.group(4));
			return builder.build();
		} else {
			return parsDescriptionBase(val, HgvsDnaDescriptions.HGVS_INSERTION_BASE_PATTERN, HgvsType.INSERTION);
		}
	}

	public static HgvsDescription parseInversionDescription(String val) {
		Matcher matcher = HgvsDnaDescriptions.HGVS_INVERSION_PATTERN.matcher(val);
		if (matcher.matches()) {
			HgvsDescriptionImpl.HgvsDescriptionBuilder builder = HgvsDescriptionImpl.builder();
			builder.description(val).type(HgvsType.INVERSION).start(Long.parseLong(matcher.group(1))).parsed(true)
					.end(Long.parseLong(matcher.group(3)));
			return builder.build();
		} else {
			return parsDescriptionBase(val, HgvsDnaDescriptions.HGVS_INVERSION_BASE_PATTERN, HgvsType.INVERSION);
		}
	}

	public static HgvsDescription parseInsertionInversionDescription(String val) {
		Matcher matcher = HgvsDnaDescriptions.HGVS_INSERTION_INVERSION_PATTERN.matcher(val);
		if (matcher.matches()) {
			HgvsDescriptionImpl.HgvsDescriptionBuilder builder = HgvsDescriptionImpl.builder();
			builder.description(val).type(HgvsType.INSERTION_INVERSION).start(Long.parseLong(matcher.group(1)))
					.parsed(true).startCross(Long.parseLong(matcher.group(3))).end(Long.parseLong(matcher.group(4)))
					.endCross(Long.parseLong(matcher.group(6)));
			return builder.build();
		} else {
			return parsDescriptionBase(val, HgvsDnaDescriptions.HGVS_INSERTION_INVERSION_BASE_PATTERN,
					HgvsType.INSERTION_INVERSION);
		}
	}

	public static HgvsDescription parseConversionDescription(String val) {
		Matcher matcher = HgvsDnaDescriptions.HGVS_CONVERSION_PATTERN.matcher(val);
		if (matcher.matches()) {
			HgvsDescriptionImpl.HgvsDescriptionBuilder builder = HgvsDescriptionImpl.builder();
			builder.description(val).type(HgvsType.CONVERSION).start(Long.parseLong(matcher.group(1))).parsed(true)
					.startCross(Long.parseLong(matcher.group(3))).end(Long.parseLong(matcher.group(7)))
					.endCross(Long.parseLong(matcher.group(9)));
			if(matcher.group(5) !=null) {
				builder.conversionSeqId(matcher.group(5 ));
			}
			return builder.build();
		} else {
			return parsDescriptionBase(val, HgvsDnaDescriptions.HGVS_CONVERSION_BASE_PATTERN, HgvsType.CONVERSION);
		}
	}

	public static HgvsDescription parseDeletionInsertionDescription(String val) {
		Matcher matcher = HgvsDnaDescriptions.HGVS_DELETION_INSERTION_PATTERN.matcher(val);
		if (matcher.matches()) {
			HgvsDescriptionImpl.HgvsDescriptionBuilder builder = HgvsDescriptionImpl.builder();
			builder.description(val).type(HgvsType.DELETION_INSERTION).start(Long.parseLong(matcher.group(1)))
					.parsed(true).varType(matcher.group(5));
			if (matcher.group(4) != null) {
				builder.end(Long.parseLong(matcher.group(4)));
			} else {
				builder.end(Long.parseLong(matcher.group(1)));
			}
			return builder.build();
		} else {
			return parsDescriptionBase(val, HgvsDnaDescriptions.HGVS_DELETION_INSERTION_BASE_PATTERN,
					HgvsType.DELETION_INSERTION);

		}
	}

	public static HgvsDescription parseRepeatDescription(String val) {
		Matcher matcher = HgvsDnaDescriptions.HGVS_REPEAT_PATTERN.matcher(val);
		if (matcher.matches()) {
			HgvsDescriptionImpl.HgvsDescriptionBuilder builder = HgvsDescriptionImpl.builder();
			builder.description(val).type(HgvsType.REPEAT).start(Long.parseLong(matcher.group(1))).parsed(true);
			if (matcher.group(4) != null) {
				builder.end(Long.parseLong(matcher.group(4)));
			}
			Map.Entry<String, Integer> repeat = new AbstractMap.SimpleEntry<>(matcher.group(5),
					Integer.parseInt(matcher.group(7)));
			builder.repeat(repeat);
			String repeats = matcher.group(9);
			while (repeats != null) {
				Matcher matcher2 = HgvsDnaDescriptions.REPEAT_PATTERN.matcher(repeats);
				if (matcher2.matches()) {
					builder.repeat(
							new AbstractMap.SimpleEntry<>(matcher2.group(1), Integer.parseInt(matcher2.group(3))));
					repeats = matcher2.group(5);
				} else {
					break;
				}
			}
			return builder.build();
		} else {
			return parsDescriptionBase(val, HgvsDnaDescriptions.HGVS_REPEAT_BASE_PATTERN, HgvsType.REPEAT);

		}
	}

	private static HgvsDescription parsDescriptionBase(String val, Pattern pattern, HgvsType type) {
		Matcher matcher = pattern.matcher(val);
		if (matcher.matches()) {
			HgvsDescriptionImpl.HgvsDescriptionBuilder builder = HgvsDescriptionImpl.builder();
			return builder.description(val).type(type).parsed(false).build();
		} else {
			throw new InvalidadHgvsDescription(val + " does not match hgvs description type: " + type);
		}
	}
}