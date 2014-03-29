package templates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// <root className="Flag" classComment="Represents Flag type.">

/**
 * Represents Flag type.
 * @author generator
 */
public enum Flag {

    /** 
     * null value. 
     * @generated
     */
    NULL(null),
    // <items upperCase="OFF" camelCase="Off" code="0" separator="," end=";">
    
    /** 
     * Off 
     * @generated 
     */
    OFF("0"),
    // </items>
    // <delete>
    /** On */
    ON("1");
    // </delete>

    /** enum code. */
    private String code;

    /**
     * constructor.
     * @generated
     * @param newCode code value
     */
    private Flag(String newCode) {
        this.code = newCode;
    }

    /**
     * gets code value.<br/>
     * @generated
     * @return code value
     */
    public String toCode() {
        return code;
    }

    private static final Map<String, Flag> MAP = new HashMap<String, Flag>();
    static {
        for (Flag type : Flag.values()) {
            MAP.put(type.toCode(), type);
        }
    }

    /**
     * Gets literal list except NULL.<br>
     * @generated
     * @return literal list
     */
    public static List<Flag> getLiteralList() {
        List<Flag> list = new ArrayList<>();
        for (Flag type : MAP.values()) {
            if (type.toCode() != null) {
                list.add(type);
            }

        }
        return list;
    }

    /**
     * Returns Enum type.<br>
     * @generated
     * @param code value
     * @return Enum type
     */
    public static Flag toEnum(String code) {
        Flag type = MAP.get(code);
        if (type == null) {
            return NULL;
        }
        return type;
    }

    /**
     * whether null value or not.<br>
     * @generated
     * @return If true, NULL value
     */
    public boolean isNull() {
        return this == NULL;
    }

    // <items upperAll="OFF" upperCamel="Off" code="0">
    /**
     * whether Off or not.<br>
     * @generated
     * @return If true,Off
     */
    public boolean isOff() {
        return this == OFF;
    }

    // <delete>
    /**
     * whether On or not.<br>
     * @return If true,On
     */
    public boolean isOn() {
        return this == ON;
    }
    // </delete>
    // </items>
    // </root>
}
