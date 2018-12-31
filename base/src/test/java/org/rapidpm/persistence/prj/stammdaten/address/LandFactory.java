package org.rapidpm.persistence.prj.stammdaten.address;

import org.rapidpm.lang.Pair;
import org.rapidpm.persistence.EntityFactory;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cernst
 * Date: 05.10.11
 * Time: 10:10
 */
public class LandFactory extends EntityFactory<Land> {
    public final List<Pair<String, String>> laender = new ArrayList<Pair<String, String>>();

    public LandFactory() {
        super(Land.class);
    }

    {
        laender.add(new Pair<String, String>("AW", "Aruba"));
        laender.add(new Pair<String, String>("AF", "Afghanistan"));
        laender.add(new Pair<String, String>("AO", "Angola"));
        laender.add(new Pair<String, String>("AI", "Anguilla"));
        laender.add(new Pair<String, String>("AX", "Åland Inseln"));
        laender.add(new Pair<String, String>("AL", "Albanien"));
        laender.add(new Pair<String, String>("AD", "Andorra"));
        laender.add(new Pair<String, String>("AN", "Niederländische Antillen"));
//AE    Vereinigte Arabische Emirate
//AR    Argentinien
//AM    Armenien
//AS    Amerikanisch Samoa
//AQ    Antarktis
//TF    Französische Südgebiete
//AG    Antigua und Barbuda
//AU    Australien
//AT    Österreich
//AZ    Aserbaidschan
//BI    Burundi
//BE    Belgien
//BJ    Benin
//BF    Burkina Faso
//BD    Bangladesch
//BG    Bulgarien
//BH    Bahrain
//BS    Bahamas
//BA    Bosnien und Herzegowina
//BY    Weißrussland
//BZ    Belize
//BM    Bermuda
//BO    Bolivien
//BR    Brasilien
//BB    Barbados
//BN    Brunei Darussalam
//BT    Bhutan
//BV    Bouvetinsel
//BW    Botsuana
//CF    Zentralafrikanische Republik
//CA    Kanada
//CC    Kokosinseln
//CH    Schweiz
//CL    Chile
//CN    China
//CI    Côte d´Ivoire
//CM    Kamerun
//CD    Kongo, Dem. Rep.
//CG    Kongo
//CK    Cookinseln
//CO    Kolumbien
//KM    Komoren
//CV    Kap Verde
//CR    Costa Rica
//CU    Kuba
//CX    Weihnachtsinsel
//KY    Kaimaninseln
//CY    Zypern
//CZ    Tschechische Republik
        laender.add(new Pair<String, String>("DE", "Deutschland"));
//DJ    Republik Dschibuti
//DM    Dominica
//DK    Dänemark
//DO    Dominikanische Republik
//DZ    Algerien
//EC    Ecuador
//EG    Ägypten
//ER    Eritrea
//EH    Westsahara
//ES    Spanien
//EE    Estland
//ET    Äthiopien
//FI    Finnland
//FJ    Fidschi
//FK    Falklandinseln
//FR    Frankreich
//FO    Färöer
//FM    Mikronesien, Föderierte Staaten von
//GA    Gabun
//GB    Vereinigtes Königreich
//GE    Georgien
//GG    Guernsey
//GH    Ghana
//GI    Gibraltar
//GN    Guinea
//GP    Guadeloupe
//GM    Gambia
//GW    Guinea-Bissau
//GQ    Äquatorialguinea
//GR    Griechenland
//GD    Grenada
//GL    Grönland
//GT    Guatemala
//GF    Französisch Guiana
        laender.add(new Pair<String, String>("GU", "Guam"));
//GY    Guyana
//HK    Hong Kong
//HM    Heard Insel und McDonald Inseln
//HN    Honduras
//HR    Kroatien
//HT    Haiti
//HU    Ungarn
//ID    Indonesien
//IM    Isle of Man
//IN    Indien
//IO    Britische Territorien im Indischen Ozean
//IE    Irland
//IR    Iran, Islam. Rep.
//IQ    Irak
//IS    Island
//IL    Israel
//IT    Italien
//JM    Jamaika
//JE    Jersey
//JO    Jordanien
//JP    Japan
//KZ    Kasachstan
//KE    Kenia
//KG    Kirgisistan
//KH    Kambodscha
//KI    Kiribati
//KN    St. Kitts und Nevis
//KR    Korea, Rep.
//KW    Kuwait
//LA    Laos, Dem. Volksrep.
//LB    Libanon
//LR    Liberia
//LY    Libysch-Arabische Dschamahirija
//LC    St. Lucia
//LI    Liechtenstein
//LK    Sri Lanka
//LS    Lesotho
//LT    Litauen
//LU    Luxemburg
//LV    Lettland
//MO    Macao
//MA    Marokko
//MC    Monaco
//MD    Moldau, Rep.
//MG    Madagaskar
//MV    Malediven
//MX    Mexiko
//MH    Marshallinseln
//MK    Mazedonien, ehemalige jugoslawische Republik
//ML    Mali
//MT    Malta
//MM    Myanmar
//ME    Montenegro
//MN    Mongolei
//MP    Nördliche Marianen
//MZ    Mosambik
//MR    Mauretanien
//MS    Montserrat
//MQ    Martinique
//MU    Mauritius
//MW    Malawi
//MY    Malaysia
//YT    Mayotte
//NA    Namibia
//NC    Neukaledonien
//NE    Niger
//NF    Norfolk Insel
//NG    Nigeria
//NI    Nicaragua
//NU    Niue
//NL    Niederlande
//NO    Norwegen
//NP    Nepal
//NR    Nauru
//NZ    Neuseeland
//OM    Oman
//PK    Pakistan
//PA    Panama
//PN    Pitcairn
//PE    Peru
//PH    Philippinen
//PW    Palau
//PG    Papua-Neuguinea
//PL    Polen
//PR    Puerto Rico
//KP    Korea, Dem. Volksrep.
//PT    Portugal
//PY    Paraguay
//PS    Palästinische Gebiete
//PF    Französisch Polynesien
//QA    Katar
//RE    Réunion
//RO    Rumänien
//RU    Russische Föderation
//RW    Ruanda
//SA    Saudi-Arabien
//SD    Sudan
//SN    Senegal
//SG    Singapur
//GS    Südgeorgien und die Südlichen Sandwichinseln
//SH    Saint Helena
//SJ    Svalbard und Jan Mayen
//SB    Salomonen
//SL    Sierra Leone
//SV    El Salvador
//SM    San Marino
//SO    Somalia
//PM    Saint Pierre und Miquelon
//RS    Serbien
//ST    São Tomé und Príncipe
//SR    Suriname
//SK    Slowakei
//SI    Slowenien
//SE    Schweden
//SZ    Swasiland
//SC    Seychellen
//SY    Syrien, Arab. Rep.
//TC    Turks- und Caicosinseln
//TD    Tschad
//TG    Togo
//TH    Thailand
//TJ    Tadschikistan
//TK    Tokelau
//TM    Turkmenistan
//TL    Timor-Leste
//TO    Tonga
//TT    Trinidad und Tobago
//TN    Tunesien
//TR    Türkei
//TV    Tuvalu
//TW    Taiwan
//TZ    Tansania, Vereinigte Rep.
//UG    Uganda
//UA    Ukraine
//UM    United States Minor Outlying Islands
//UY    Uruguay
//US    Vereinigte Staaten von Amerika
//UZ    Usbekistan
//VA    Heiliger Stuhl
//VC    St. Vincent und die Grenadinen
//VE    Venezuela
//VG    Britische Jungferninseln
//VI    Amerikanische Jungferninseln
//VN    Vietnam
//VU    Vanuatu
//WF    Wallis und Futuna
//WS    Samoa
//YE    Jemen
//ZA    Südafrika
        laender.add(new Pair<String, String>("ZM", "Sambia"));
        laender.add(new Pair<String, String>("ZW", "Simbabwe"));
    }

    @Override
    public Land createRandomEntity() {
        final Land land = new Land();
        final Pair<String, String> pair = RND.nextElement(laender);
        land.setIsoCode(combineStringWithNextIndex(pair.getValue1()));
        land.setName(combineStringWithNextIndex(pair.getValue2()));
        return land;
    }

    @Test
    public void testCreate() throws Exception {
        System.out.println(createRandomEntity());
    }
}
