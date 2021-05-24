package com.xivs.client.gui.components.builders;

import com.xivs.common.Utils.Pair;
import com.xivs.common.lab.Address;
import com.xivs.common.lab.Organization;
import com.xivs.common.lab.OrganizationType;

import javax.swing.*;
import java.awt.*;

public class OrganizationBuilderForm extends BuilderForm<Organization> {
    JTextField annualTurnoverField;
    JComboBox<OrganizationType> organizationTypeField;

    public OrganizationBuilderForm(Dimension size, Organization defaultValue){
        super(size, 2, defaultValue);
    }
    @Override
    public Organization build() {
        if(!Organization.Params.annualTurnover.parse(annualTurnoverField.getText())) return null;
        OrganizationType type = OrganizationType.NONE;
        if(organizationTypeField.isEnabled()) type = (OrganizationType) organizationTypeField.getSelectedItem();
        return new Organization(Organization.Params.annualTurnover.get(), type, Address.DEFAULT);
    }
    @Override
    void initDefaultValues(){
        annualTurnoverField.setText(String.valueOf(defaultValue.annualTurnover));
        organizationTypeField.setSelectedItem(defaultValue.type);
    }
    @Override
    void initFields() {
        annualTurnoverField = new JTextField();
        organizationTypeField = new JComboBox<>();
        for (OrganizationType t: OrganizationType.values()){
            organizationTypeField.addItem(t);
        }
        fields.put(res.getString("annual_turnover"), new Pair<>(annualTurnoverField, false));
        fields.put(res.getString("organization_type"), new Pair<>(organizationTypeField, true));
    }
}
