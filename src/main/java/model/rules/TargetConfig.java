package model.rules;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Objects;


/**
 * Configuration class to define targeting settings for transformation rules.
 * This class specifies what parts of an HTML element should be targeted
 * (such as text, attributes, or tags) and optionally, specific attributes or tags.
 */
public class TargetConfig {

    /**
     * Enum to define the types of targets that transformation rules can apply to.
     */
    public enum TargetType {
        TEXT, ATTRIBUTES, TAGS
    }

    private EnumSet<TargetType> targetTypes;
    private Map<TargetType, Set<String>> specificTargets;

    /**
     * Constructs a new TargetConfig with specified target types and specific targets.
     *
     * @param targetTypes      the types of targets as an EnumSet of TargetType
     * @param selectAllTypes   the types of targets that have all elements selected
     * @param specificTargets  a map of TargetType to sets of strings that specify
     *                         particular attributes or tags. If the targetType is in targetTypes
     *                         it will ignore the specificTargets and vice versa
     */
    public TargetConfig(EnumSet<TargetType> targetTypes, Map<TargetType, Set<String>> specificTargets) {
        if (targetTypes == null) targetTypes = EnumSet.noneOf(TargetType.class);
        if (specificTargets == null) specificTargets = new HashMap<>();

        this.targetTypes = targetTypes;
        this.specificTargets = specificTargets;
    }

    /**
     * Returns the set of target types.
     *
     * @return an EnumSet of TargetType indicating the types of elements to be targeted
     */
    public EnumSet<TargetType> getTargetTypes() {
        return targetTypes;
    }

    /**
     * Returns the map of specific targets.
     *
     * @return a map from TargetType to sets of strings, each set containing specific
     *         names of attributes or tags to be included or excluded
     */
    public Map<TargetType, Set<String>> getSpecificTargets() {
        return specificTargets;
    }

    /**
     * Checks if this target configuration should check the elements
     * of the targetType.
     *
     * @param targetType the type of target to check for
     * @return a boolean of if the target type is valid for this TargetConfig
     */
    public boolean checkTarget(TargetType targetType) {
        return targetTypes.contains(targetType);
    }

    /**
     * Checks if the target type has the `select all` setting on.
     *
     * @param targetType the type of target to check for
     * @return a boolean of if the target type is valid for this TargetConfig
     */
    public boolean selectAll(TargetType targetType) {
        return !checkTarget(targetType);
    }

    public boolean containsSpecific(TargetType targetType, String specific) {
        Set<String> specificSet = getSpecificTargets().get(targetType);
        return specificSet != null && specificSet.contains(specific);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TargetConfig that = (TargetConfig) obj;
        return targetTypes.equals(that.targetTypes) &&
               specificTargets.equals(that.specificTargets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetTypes, specificTargets);
    }
}


