//
//  RegisterModel.swift
//  SoftFact
//
//  Created by Магжан Бекетов on 12.04.2021.
//

import Foundation
import Alamofire

enum APIErrors : Error {
    case custom (message: String)
}

typealias Handler = (Swift.Result<Any?, APIErrors>) -> Void

class APIManager {
    static let shareInstance = APIManager()
    private var decoder: JSONDecoder = JSONDecoder()
    var userError : ValidationError?
    
    func callingRegisterAPI(register : UserRegistDTO , completionHandler: @escaping([String], Bool) -> () ){
        let headers : HTTPHeaders = [
            .contentType("application/json")]
    
        AF.request(Constants.registration, method: .post, parameters: register, encoder: JSONParameterEncoder.default, headers: headers).response{ response in
            //debugPrint(response)
            
            var sendingErrorArr : [String] = []
            switch response.result{
            case .success(_):
                do{
                   
                    
                    guard let data = response.data else { return }
                    if let error = try? self.decoder.decode(ValidationError.self, from: data) {
                        self.userError = error
                    }
                    
                    print("... its userError\(String(describing: self.userError?.localizedMessage))")
                    
                    if response.response?.statusCode == 200 {
                        sendingErrorArr.append("Succesfull")
                        completionHandler(sendingErrorArr,true)
                        sendingErrorArr.removeAll()

                    }else{
                        if let messages = self.userError?.messages {
                            sendingErrorArr = messages as! [String]
                            completionHandler((sendingErrorArr),false)
                            print("sendingErrorArr")
                            sendingErrorArr.removeAll()
                        }else{
                        
                            guard let message = self.userError?.localizedMessage else {
                                print("... guard let message nil but not 200")
                                return
                            }
                            
                            sendingErrorArr.append(message)
                            completionHandler((sendingErrorArr),false)
                            sendingErrorArr.removeAll()
                        }
                    }
                    }
            case .failure(let err):
                print("BIG FAILE")
                sendingErrorArr.append(err.localizedDescription)
                completionHandler(sendingErrorArr,false)
                sendingErrorArr.removeAll()

            }
        }
    
    }
    
    func callingLoginAPI(login : Login , completionHandler : @escaping Handler){
        let headers : HTTPHeaders = [
            .contentType("application/json")]
    
        AF.request(Constants.login, method: .post, parameters: login, encoder: JSONParameterEncoder.default, headers: headers).response{ response in
            debugPrint(response)
            
            switch response.result{
            case .success(let data):
                do{
                    let json = try JSONSerialization.jsonObject(with: data!, options: [])
                    
                    guard let data = response.data else { return }
                   
                    if response.response?.statusCode == 200 {
                        completionHandler(.success(json))
                    }else{
                        if let error = try? self.decoder.decode(ValidationError.self, from: data) {
                            self.userError = error
                        }
                        if let message = self.userError?.messages.first {
                            completionHandler(.failure(.custom(message: message!)))
                        }
                    }
                    }catch{
                        completionHandler(.failure(.custom(message: "Please try again")))
                }
            case .failure(let err):
                completionHandler(.failure(.custom(message: String(describing: err))))
            }
        }
    
    }
}

