//
//  LoginViewController.swift
//  SoftFact
//
//  Created by Магжан Бекетов on 12.04.2021.
//

import UIKit

class LoginViewController: UIViewController {

    @IBOutlet weak var usernameTextField: UITextField!
    @IBOutlet weak var passwordTextField: UITextField!
    @IBOutlet weak var logInButton: UIButton!
    @IBOutlet weak var errorLabel: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setUpElements()
    }
    
    func setUpElements() -> () {
        
        errorLabel.alpha = 0
        
        Utilities.styleTextField(usernameTextField)
        Utilities.styleTextField(passwordTextField)
        Utilities.styleFilledButton(logInButton)
    }

    @IBAction func loginTaped(_ sender: Any) {
        guard let username = usernameTextField.text else {
            print("... username is nil")
            return
        }
        guard let password = passwordTextField.text else {
            print("... password is nil")
            return
        }
     
        let loginUser = Login(username: username, password: password)
        
        APIManager.shareInstance.callingLoginAPI(login:  loginUser) { (result) in
            
            switch result {
            case .success(let json):
                
                //print(json as AnyObject)
                
                let username = (json as AnyObject).value(forKey: "username") as! String
                let email = (json as AnyObject).value(forKey: "email") as! String
                let birthDate = (json as AnyObject).value(forKey: "birthDate") as! String
                let role = (json as AnyObject).value(forKey: "role") as AnyObject
                let name = role.value(forKey: "name") as! String
                
//                print("... \(name)")
                let userRole = Role(name: name)
//                print("... \(userRole.name)")
                let user = User(username: username, email: email, birthDate: birthDate,role: userRole)
                
                self.errorLabel.textColor = .green
                self.errorLabel.alpha = 1
                self.errorLabel.numberOfLines = 0
                self.errorLabel.lineBreakMode = NSLineBreakMode.byWordWrapping
                self.errorLabel.text = "\(user)"
                self.redirectToMain(user: user)
                
            case .failure(let error):
                print("... \(error)")
                self.errorLabel.textColor = .red
                self.errorLabel.alpha = 1
                self.errorLabel.numberOfLines = 0
                self.errorLabel.lineBreakMode = NSLineBreakMode.byWordWrapping
                let message = self.clearMessage(message: "\(error)")
                self.errorLabel.text = "\(message)"
            }
        }
    }
    
    private func clearMessage(message : String) ->String {
        let start = message.index(message.startIndex, offsetBy: 17)
        let end = message.index(message.endIndex, offsetBy: -2)
        let range = start..<end
        return String(message[range])
    }
    
    private func redirectToMain(user : User) {
//        let storyboard = UIStoryboard(name: "Main", bundle: Bundle.main)
//        if let vc = storyboard.instantiateViewController(withIdentifier: "ProfileViewController") as? ProfileViewController {
//            vc.user = user
//        }
        let vc = ProfileViewController()
        vc.user = user
        let tabBarController = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: "TabBarController")
          if let navigator = self.navigationController {
               navigator.pushViewController(tabBarController, animated: true)
      }
    }
}
