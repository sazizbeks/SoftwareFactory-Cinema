//
//  Register.swift
//  SoftFact
//
//  Created by Магжан Бекетов on 13.04.2021.
//

import Foundation

public struct UserRegistDTO : Encodable{
    var username : String?
    var password : String?
    var rePassword : String?
    var email : String?
    var birthDate : String?
}

public struct ValidationError : Codable{
    var localizedMessage : String?
    var messages : [String?]
}

