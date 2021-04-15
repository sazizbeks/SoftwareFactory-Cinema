//
//  SignUpViewController.swift
//  SoftFact
//
//  Created by Магжан Бекетов on 12.04.2021.
//

import UIKit

class SignUpViewController: UIViewController {

    @IBOutlet weak var username: UITextField!
    @IBOutlet weak var password: UITextField!
    @IBOutlet weak var rePassword: UITextField!
    @IBOutlet weak var email: UITextField!
    @IBOutlet weak var errorLbl: UILabel!
    @IBOutlet weak var signUp: UIButton!
    @IBOutlet weak var datePicker: UIDatePicker!
    
    lazy var errorsStr : String = ""
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        setUpElements()
        
    }
    
    func setUpElements(){
        
        errorLbl.alpha = 0
        
        Utilities.styleTextField(username)
        Utilities.styleTextField(password)
        Utilities.styleTextField(rePassword)
        Utilities.styleTextField(email)
        Utilities.styleFilledButton(signUp)
    
    
    }

    @IBAction func loginTap(_ sender: Any) {
        
     
        guard let username = username.text else {
            return
        }
        guard let password = password.text else {
            return
        }
        guard let rePassword = rePassword.text else {
            return
        }
        guard let email = email.text else {
            return
        }

        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy-mm-dd"
        let dateString = dateFormatter.string(from: datePicker.date)
        
        print("... \(dateString)")
        
        let regist = UserRegistDTO(username: username, password: password, rePassword: rePassword, email: email, birthDate: dateString)
        
        APIManager.shareInstance.callingRegisterAPI(register: regist) { (errorStr, isSuccess) in
            self.errorLbl.alpha = 1
            
            self.errorsStr.removeAll()
            
            for item in errorStr{
                self.errorsStr += "\(item) \n"
            }
            
            if isSuccess {
                self.errorLbl.alpha = 1
                self.errorLbl.textColor = .green
                self.errorLbl.numberOfLines = 0
                self.errorLbl.lineBreakMode = NSLineBreakMode.byWordWrapping
                self.errorLbl.text = self.errorsStr
                self.navigationController?.popViewController(animated: true)
            }else{
                self.errorLbl.alpha = 1
                self.errorLbl.textColor = .red
                self.errorLbl.numberOfLines = 0
                self.errorLbl.lineBreakMode = NSLineBreakMode.byWordWrapping
                self.errorLbl.text = self.errorsStr
            }
        }
    }

}

