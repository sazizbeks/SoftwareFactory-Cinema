//
//  ProfileViewController.swift
//  SoftFact
//
//  Created by Магжан Бекетов on 14.04.2021.
//

import UIKit

class ProfileViewController: UIViewController {

    var user : User?
    
    var username : String!
    var email : String!
    var birthday : String!
    
    @IBOutlet weak var usernameLabel: UILabel!
    @IBOutlet weak var emailLabel: UILabel!
    @IBOutlet weak var birthdayLabel: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        setUpData()
        
    }
    
    private func setUpData(){
//        usernameLabel.text = username
//        emailLabel.text = email
//        birthdayLabel.text = birthday
        
        guard let user = user else{
            print("... setUpData user is nil")
            return
        }
        if let username = user.username{
            usernameLabel.text = username
        }
        if let email = user.email {
            emailLabel.text = email
        }
        if let birthday = user.birthDate {
            birthdayLabel.text = birthday
        }
    }
}
