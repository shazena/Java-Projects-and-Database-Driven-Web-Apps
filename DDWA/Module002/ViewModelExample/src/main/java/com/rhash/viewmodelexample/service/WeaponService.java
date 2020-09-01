package com.rhash.viewmodelexample.service;

import com.rhash.viewmodelexample.dao.WeaponDao;
import com.rhash.viewmodelexample.dto.Weapon;
import com.rhash.viewmodelexample.dto.WeaponVM;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author rhash
 */
@Service
public class WeaponService {

    @Autowired
    WeaponDao weaponDao;

    // //////////////////////////////////////////////////
    // Regular Model Methods
    public List<Weapon> GetAllWeapons() {
        return weaponDao.GetAllWeapons();
    }

    public Weapon GetWeaponByName(String name) {
        return weaponDao.GetWeaponByName(name);
    }

    public Weapon AddWeapon(Weapon weapon) {
        return weaponDao.AddWeapon(weapon);
    }

    public Weapon EditWeapon(Weapon weapon) {
        return weaponDao.EditWeapon(weapon);
    }

    public void RemoveWeaponByName(String name) {
        weaponDao.DeteleWeaponByName(name);
    }

    // //////////////////////////////////////////////////
    // View Model Methods
    public List<WeaponVM> GetAllWeaponVMs() {
        List<Weapon> allWeapons = weaponDao.GetAllWeapons();
        List<WeaponVM> allWeaponVMs = new ArrayList<>();
        for (Weapon weapon : allWeapons) {
            allWeaponVMs.add(convertToViewModel(weapon));
        }
        return allWeaponVMs;
    }

    public Weapon GetWeaponVMByName(String name) {
        return weaponDao.GetWeaponByName(name);
    }

    public Weapon AddWeaponVM(Weapon weapon) {
        return weaponDao.AddWeapon(weapon);
    }

    public Weapon EditWeaponVM(Weapon weapon) {
        return weaponDao.EditWeapon(weapon);
    }

    public void RemoveWeaponVMByName(String name) {
        weaponDao.DeteleWeaponByName(name);
    }

    private WeaponVM convertToViewModel(Weapon weapon) {
        WeaponVM weaponVM = new WeaponVM();
        weaponVM.setName(weapon.getName());
        weaponVM.setWeight(weapon.getWeight() + " lbs");
        weaponVM.setAttackPower(weapon.getAttackPower() + " power");

        String weaponDescription = "";

        if (weapon.isIsBeautiful()) {
            weaponDescription += "Beautiful ";
        } else {
            weaponDescription += "Ugly ";
        }

        if (weapon.getWeight() < 10) {
            weaponDescription += "Light ";
        } else if (weapon.getWeight() < 50) {
            weaponDescription += "Medium ";
        } else {
            weaponDescription += "Heavy ";
        }

        if (weapon.isIsMagical()) {
            weaponDescription += "Magic ";
        } else {
            weaponDescription += "Physical ";
        }
        weaponVM.setDescription(weaponDescription);

        return weaponVM;
    }
}
