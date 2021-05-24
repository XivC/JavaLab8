package com.xivs.client.gui.components.builders;

import com.xivs.common.Utils.Pair;
import com.xivs.common.lab.Address;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class AddressBuilderForm extends BuilderForm<Address> {
    JTextField streetField;
    JTextField zipCodeField;
    public AddressBuilderForm(Dimension size, Address defaultValue) {

        super(size,2, defaultValue);
    }

    @Override
    public Address build() {
        Address ret = new Address();
        ret.street = streetField.isEnabled() ? streetField.getText():"";
        if(!Address.Params.zipCode.parse(zipCodeField.getText())) return null;
        ret.zipCode = Address.Params.zipCode.get();
        return ret;

    }
    @Override
    void initDefaultValues(){
        streetField.setText(defaultValue.street);
        zipCodeField.setText(String.valueOf(defaultValue.zipCode));

    }

    @Override
    void initFields() {
        streetField = new JTextField();
        zipCodeField = new JTextField();
        fields.put(res.getString("address"), new Pair<>(streetField, true));
        fields.put(res.getString("zip_code"), new Pair<>(zipCodeField, false));


    }
}
