//
//  User.swift
//  SoftFact
//
//  Created by Магжан Бекетов on 12.04.2021.
//

import Foundation

public struct Role : Codable{
    var name : String?
}

public struct User : Codable {
    var username : String?
    var email : String?
    var birthDate : String?
    var role : Role?
}


