/**
 * created since 2009-7-20
 */
package com.huaixuan.network.biz.enums.hy;

import java.util.HashMap;
import java.util.Map;

/**
 * @author songfy
 */
public enum EnumBrandType {

	BALENCIAGA("1","BALENCIAGA"),
	CHANEL("2","CHANEL"),
	FENDI("3","FENDI"),
	GUCCI("4","GUCCI"),
	LOUIS_VUITTON("5","LOUIS VUITTON"),
	PRADA("6","PRADA"),
	DOLCE_GABBANA("7","DOLCE&GABBANA"),
	BOTTEGA_VENETA ("8","BOTTEGA VENETA"),
	BURBERRY("9","BURBERRY"),
	MIUMIU("10","MIU MIU"),
	STELL_MCCARTNEY("11","STELL MCCARTNEY"),
	CHLOE("12","CHLOE"),
	BVLGARI("13","BVLGARI"),
	COACH("14","COACH"),
	DIOR("15","DIOR"),
	Salvatore_Ferragamo("16","Salvatore Ferragamo"),
	GIVENCHY("17","GIVENCHY"),
	HERMES("18","HERMES"),
	JIMMY_CHOO("19","JIMMY CHOO"),
	LOEWE("20","LOEWE"),
	MARC_JACOBS("21","MARC JACOBS"),
	TOD_S("22","TOD'S"),
	VALENTINO("23","VALENTINO"),
	VERSACE("24","VERSACE"),
	YSL("25","YSL"),
	Ermenegildo_Zegna("26","Ermenegildo Zegna"),
	EMPORIO_ARMANI("27","EMPORIO ARMANI"),
	ARMANI_COLLEZIONI("28","ARMANI COLLEZIONI"),
	GIORGIO_ARMANI("29","GIORGIO ARMANI"),
	BALLY("30","BALLY"),
	RENATO_ANGI("31"," "),
	MONCLER("32","MONCLER"),
	DSQUARED("33","DSQUARED"),
	JUICY_COUTURE("34","JUICY COUTURE"),
	LANVIN("35","LANVIN"),
	CELINE("36","CELINE"),
	BALMAIN("37","BALMAIN"),
	GIANVITO_ROSSI("38","GIANVITO ROSSI"),
	DUVETICA("39","DUVETICA"),
	LONGCHANMPS("40","LONGCHANMPS"),
	NEIL_BURRET("41","NEIL BURRET"),
	HUGO_BOSS("42","HUGO BOSS"),
	MULBERRY("43","MULBERRY"),
	PROEZA_SCHOOLER("44","PROEZA SCHOOLER"),
	GOLDEN_GOOSE("45","GOLDEN GOOSE"),
	TORY_BURCH("46","TORY BURCH"),
	MARNI("47","MARNI"),
	MAXMARA("48","MAXMARA"),
	FOSSIL("49","FOSSIL"),
	CELYN_B("50","CELYN B"),
	TWIN_SET("51","TWIN-SET"),
	REED_KRAKOFF("52","REED KRAKOFF"),
	ALEXANDER_MCQUEEN("53","ALEXANDER MCQUEEN"),
	ALEXANDER_WANG("54","ALEXANDER WANG"),
	MONTBLANC("55","MONTBLANC"),
	MICHAEL_KORS("56","MICHAEL KORS"),
	RAY_BAN("57","RAY BAN"),
	ROBERTO_CAVALLI("58","ROBERTO CAVALLI"),
	MARC_BY_MARC_JACOBS("59","MARC BY MARC JACOBS"),
	POLICE("60","POLICE"),
	JUST_CAVALLI("61","JUST CAVALLI"),
	IT_INDIPENDENT("62","IT.INDIPENDENT"),
	PINKO("63","PINKO"),
	CHRISTIAN_LOUBOUTIN("64","CHRISTIAN LOUBOUTIN"),
	BLUGIRL("65","BLUGIRL"),
	CALVIN_KLEIN("66","CALVIN KLEIN"),
	ERMENEGILDO_ZEGNA("67","ERMENEGILDO ZEGNA"),
	ROGER_VIVER("68","ROGER VIVER"),
	UGG("69","UGG"),
	MONCLER_S("70","MONCLER S"),
	MONCLER_GRENOBLE("71","MONCLER GRENOBLE"),
	CARTIER("72","CARTIER"),
	GOYARD("73","GOYARD"),
	KENZO("74","KENZO"),
	COMME_DES_GARCON("75","COMME DES GARCON"),
	PHILIPP_PLEIN("76","PHILIPP PLEIN"),
	TOM_FORD("77","TOM FORD"),
	WANT("78","WANT"),
	ARMANI_JEANS("79","ARMANI JEANS"),
	DELPHINE_DELAFON("80","DELPHINE DELAFON"),
	GIUSEPPE_ZANOTTI("100","GIUSEPPE ZANOTTI"),
	KENZO_JUNIOR("112","KENZO JUNIOR"),
	BURBERRY_JUNIOR("113","BURBERRY JUNIOR"),
	DOLCEGABBANA_JUNIOR("114","DOLCE&GABBANA JUNIOR"),
	MONCLER_JUNIOR("115","MONCLER JUNIOR"),
	MARCELO_BURLON_JUNIOR("116","MARCELO BURLON JUNIOR"),
	MSGM_JUNIOR("117","MSGM JUNIOR"),
	Salvatore_Ferragamo_JUNIOR("118","MSGM JUNIOR");
	
    private String code;

    private String name;

    EnumBrandType(String code,String name){
        this.code=code;
        this.name=name;
    }

    public String getKey() {
        return this.code;
    }
    public String getValue(){
       return this.name;
    }

    public static Map<String, String> toMap() {
        Map<String, String> enumDataMap = new HashMap<String, String>();
        enumDataMap.put(BALENCIAGA.getKey(), BALENCIAGA.getValue());
        enumDataMap.put(CHANEL.getKey(), CHANEL.getValue());
        enumDataMap.put(FENDI.getKey(), FENDI.getValue());
        enumDataMap.put(GUCCI.getKey(), GUCCI.getValue());
        enumDataMap.put(LOUIS_VUITTON.getKey(),LOUIS_VUITTON.getValue());
        enumDataMap.put(PRADA.getKey(), PRADA.getValue());
        enumDataMap.put(DOLCE_GABBANA.getKey(), DOLCE_GABBANA.getValue());
        enumDataMap.put(BOTTEGA_VENETA.getKey(), BOTTEGA_VENETA.getValue());
        enumDataMap.put(BURBERRY.getKey(), BURBERRY.getValue());
        enumDataMap.put(MIUMIU.getKey(), MIUMIU.getValue());
        enumDataMap.put(STELL_MCCARTNEY.getKey(), STELL_MCCARTNEY.getValue());
        enumDataMap.put(CHLOE.getKey(), CHLOE.getValue());
        enumDataMap.put(BVLGARI.getKey(), BVLGARI.getValue());
        enumDataMap.put(COACH.getKey(), COACH.getValue());
        enumDataMap.put(DIOR.getKey(), DIOR.getValue());
        enumDataMap.put(Salvatore_Ferragamo.getKey(), Salvatore_Ferragamo.getValue());
        enumDataMap.put(GIVENCHY.getKey(), GIVENCHY.getValue());
        enumDataMap.put(HERMES.getKey(), HERMES.getValue());
        enumDataMap.put(JIMMY_CHOO.getKey(), JIMMY_CHOO.getValue());
        enumDataMap.put(LOEWE.getKey(), LOEWE.getValue());
        enumDataMap.put(MARC_JACOBS.getKey(), MARC_JACOBS.getValue());
        enumDataMap.put(TOD_S.getKey(), TOD_S.getValue());
        enumDataMap.put(VALENTINO.getKey(), VALENTINO.getValue());
        enumDataMap.put(VERSACE.getKey(), VERSACE.getValue());
        enumDataMap.put(YSL.getKey(), YSL.getValue());
        enumDataMap.put(Ermenegildo_Zegna.getKey(), Ermenegildo_Zegna.getValue());
        enumDataMap.put(EMPORIO_ARMANI.getKey(), EMPORIO_ARMANI.getValue());
        enumDataMap.put(ARMANI_COLLEZIONI.getKey(), ARMANI_COLLEZIONI.getValue());
        enumDataMap.put(GIORGIO_ARMANI.getKey(), GIORGIO_ARMANI.getValue());
        enumDataMap.put(BALLY.getKey(), BALLY.getValue());
        enumDataMap.put(RENATO_ANGI.getKey(), RENATO_ANGI.getValue());
        enumDataMap.put(MONCLER.getKey(), MONCLER.getValue());
        enumDataMap.put(DSQUARED.getKey(), DSQUARED.getValue());
        enumDataMap.put(JUICY_COUTURE.getKey(), JUICY_COUTURE.getValue());
        enumDataMap.put(LANVIN.getKey(), LANVIN.getValue());
        enumDataMap.put(CELINE.getKey(), CELINE.getValue());
        enumDataMap.put(BALMAIN.getKey(), BALMAIN.getValue());
        enumDataMap.put(GIANVITO_ROSSI.getKey(), GIANVITO_ROSSI.getValue());
        enumDataMap.put(DUVETICA.getKey(), DUVETICA.getValue());
        enumDataMap.put(LONGCHANMPS.getKey(), LONGCHANMPS.getValue());
        enumDataMap.put(NEIL_BURRET.getKey(), NEIL_BURRET.getValue());
        enumDataMap.put(HUGO_BOSS.getKey(),HUGO_BOSS.getValue());
        enumDataMap.put(MULBERRY.getKey(), MULBERRY.getValue());
        enumDataMap.put(PROEZA_SCHOOLER.getKey(), PROEZA_SCHOOLER.getValue());
        enumDataMap.put(GOLDEN_GOOSE.getKey(), GOLDEN_GOOSE.getValue());
        enumDataMap.put(TORY_BURCH.getKey(), TORY_BURCH.getValue());
        enumDataMap.put(MARNI.getKey(), MARNI.getValue());
        enumDataMap.put(MAXMARA.getKey(), MAXMARA.getValue());
        enumDataMap.put(FOSSIL.getKey(), FOSSIL.getValue());
        enumDataMap.put(CELYN_B.getKey(), CELYN_B.getValue());
        enumDataMap.put(TWIN_SET.getKey(), TWIN_SET.getValue());
        enumDataMap.put(REED_KRAKOFF.getKey(), REED_KRAKOFF.getValue());
        enumDataMap.put(ALEXANDER_MCQUEEN.getKey(), ALEXANDER_MCQUEEN.getValue());
        enumDataMap.put(ALEXANDER_WANG.getKey(), ALEXANDER_WANG.getValue());
        enumDataMap.put(MONTBLANC.getKey(), MONTBLANC.getValue());
        enumDataMap.put(MICHAEL_KORS.getKey(), MICHAEL_KORS.getValue());
        
        enumDataMap.put(RAY_BAN.getKey(), RAY_BAN.getValue());
        enumDataMap.put(ROBERTO_CAVALLI.getKey(), ROBERTO_CAVALLI.getValue());
        enumDataMap.put(MARC_BY_MARC_JACOBS.getKey(), MARC_BY_MARC_JACOBS.getValue());
        enumDataMap.put(POLICE.getKey(), POLICE.getValue());
        enumDataMap.put(JUST_CAVALLI.getKey(), JUST_CAVALLI.getValue());
        enumDataMap.put(IT_INDIPENDENT.getKey(), IT_INDIPENDENT.getValue());
        enumDataMap.put(PINKO.getKey(), PINKO.getValue());
        enumDataMap.put(CHRISTIAN_LOUBOUTIN.getKey(), CHRISTIAN_LOUBOUTIN.getValue());
        enumDataMap.put(BLUGIRL.getKey(), BLUGIRL.getValue());
        enumDataMap.put(CALVIN_KLEIN.getKey(), CALVIN_KLEIN.getValue());
        enumDataMap.put(ERMENEGILDO_ZEGNA.getKey(), ERMENEGILDO_ZEGNA.getValue());
        enumDataMap.put(ROGER_VIVER.getKey(), ROGER_VIVER.getValue());
        enumDataMap.put(UGG.getKey(), UGG.getValue());
        enumDataMap.put(MONCLER_S.getKey(), MONCLER_S.getValue());
        enumDataMap.put(MONCLER_GRENOBLE.getKey(), MONCLER_GRENOBLE.getValue());
        enumDataMap.put(CARTIER.getKey(), CARTIER.getValue());
        enumDataMap.put(GOYARD.getKey(), GOYARD.getValue());
        enumDataMap.put(KENZO.getKey(), KENZO.getValue());
        enumDataMap.put(COMME_DES_GARCON.getKey(), COMME_DES_GARCON.getValue());
        enumDataMap.put(PHILIPP_PLEIN.getKey(), PHILIPP_PLEIN.getValue());
        enumDataMap.put(TOM_FORD.getKey(), TOM_FORD.getValue());
        enumDataMap.put(WANT.getKey(), WANT.getValue());
        enumDataMap.put(ARMANI_JEANS.getKey(), ARMANI_JEANS.getValue());
        enumDataMap.put(DELPHINE_DELAFON.getKey(), DELPHINE_DELAFON.getValue());
        
        return enumDataMap;
    }
    
    
    
}
