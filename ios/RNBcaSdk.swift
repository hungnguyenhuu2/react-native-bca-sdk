//
//  RNBcaSdk.swift
//  RNBcaSdk
//
//  Created by Khanh Khau - Ban Vien on 21/12/2022.
//

import Foundation
import QKMRZParser
import SwiftyTesseract
import NFCReader

@available(iOS 15, *)
@objc(RNBcaSdk)
class RNBcaSdk: NSObject {
  
    var bridge: RCTBridge!
    
    private var callbackSuccess: RCTPromiseResolveBlock?
    private var callbackFailure: RCTPromiseRejectBlock?
    
    private let passportReader = PassportReader()
    
    @objc static func requiresMainQueueSetup() -> Bool { return true }
    
    @available(iOS 15, *)
    @objc func readCard(_ options: Dictionary<String, String>, resolver: @escaping RCTPromiseResolveBlock, rejecter: @escaping RCTPromiseRejectBlock) -> Void {
        let masterListURL = Bundle.main.url(forResource: "masterList", withExtension: ".pem")!
        passportReader.setMasterListURL( masterListURL )
        
        let pptNr = options["idNumber"]!
        let dob = options["dob"]!
        let doe = options["doe"]!
        
        let passportUtils = PassportUtils()
        let mrzKey = passportUtils.getMRZKey( passportNumber: pptNr, dateOfBirth: dob, dateOfExpiry: doe)
        
        Task {
            do {
                let passport = try await passportReader.readPassport( mrzKey: mrzKey)
                
                let imageData:NSData = passport.passportImage!.pngData()! as NSData
                
                let resultsDict = [
                     "firstName": passport.firstName,
                     "lastName": passport.lastName,
                     "passportMrz": passport.passportMRZ,
                     "placeOfBirth": passport.placeOfBirth,
                     "residenceAddress": passport.residenceAddress,
                     "phoneNumber": passport.phoneNumber,
                     "gender": passport.gender,
                     "personalNumber": passport.personalNumber,
                     "dob": passport.dateOfBirth,
                     "nationality": passport.nationality,
                     "doe": passport.documentExpiryDate,
                     "documentNumber": passport.documentNumber,
                     "issuingAuthority": passport.issuingAuthority,
                     "faceImageBase64": imageData.base64EncodedString(options: .endLineWithLineFeed),
                     "facePhoto": passport.passportImage!.jpegData(compressionQuality: 1)?.base64EncodedString(),
                     "com": Data(passport.getDataGroup(.COM)?.data ?? []).base64EncodedString(),
                     "sod": Data(passport.getDataGroup(.SOD)?.data ?? []).base64EncodedString(),
                     "dg1": Data(passport.getDataGroup(.DG1)?.data ?? []).base64EncodedString(),
                     "dg2": Data(passport.getDataGroup(.DG2)?.data ?? []).base64EncodedString(),
                     "dg13": Data(passport.getDataGroup(.DG13)?.data ?? []).base64EncodedString(),
                     "dg14": Data(passport.getDataGroup(.DG14)?.data ?? []).base64EncodedString(),
                     "dg15": Data(passport.getDataGroup(.DG15)?.data ?? []).base64EncodedString(),
                ];
                resolver(resultsDict)
            } catch let err {
                rejecter("FAILED", "Error", err)
            }
        }
    }
    
    @available(iOS 15, *)
    @objc func readCardFIS(_ options: Dictionary<String, String>, resolver: @escaping RCTPromiseResolveBlock, rejecter: @escaping RCTPromiseRejectBlock) -> Void {
        
        callbackSuccess = resolver
        callbackFailure = rejecter
        
        let pptNr = options["idNumber"]!
        let dob = options["dob"]!
        let doe = options["doe"]!
        let token = options["token"]!
        
        let nfc = NFCManager(dateOfBirthYYMMDD: dob,
                             dateOfExpireYYMMDD: doe,
                             cardID: pptNr,
                             isCheckBCA: true)
        
        // Set URL API
        nfc.setDomainURL("https://apig.idcheck.xplat.online/real-id/v1/api-gateway/check-nfc-objdg")
        // Set Token
        nfc.setToken(token)
        nfc.scanPassport()
        nfc.delegate = self
    }
    
}

@available(iOS 15, *)
extension RNBcaSdk: NFCDelegate {
    func NFCFail(_ error: NFCReader.NFCPassportReaderError) {
        self.callbackFailure!("FAILED", "Error", error)
    }
    
    func VerifySuccess(jsonData: NFCReader.JSON) {
        let resultsDict = [
             "success": true,
             "rawData": jsonData.rawString(.utf8, options: JSONSerialization.WritingOptions.init(rawValue: 0))
        ] as [String : Any];
        self.callbackSuccess!(resultsDict)
    }
    
    func VerifyFail(_ error: NFCReader.AFError) {
        self.callbackFailure!("FAILED", "Error", error)
    }
    
    func NFCMessageDisplay(_ messages: NFCReader.NFCViewDisplayMessage) -> String {
        switch messages {
        case .requestPresentPassport:
            return "Giữ thiết bị và giấy tờ gần nhau để đọc thông tin qua NFC."
        case .authenticatingWithPassport(let progress):
            let progressString = handleProgress(percentualProgress: progress)
            return "Authenticating with passport.....\n\n\(progressString)"
        case .readingDataGroupProgress(let dataGroup, let progress):
            let progressString = handleProgress(percentualProgress: progress)
            return "Đang đọc thông tin \(dataGroup).....\n\n\(progressString)"
        case .error(let tagError):
            switch tagError {
                case .TagNotValid:
                    return "Tag not valid."
                case .MoreThanOneTagFound:
                    return "More than 1 tags was found. Please present only 1 tag."
                case .ConnectionError:
                    return "Connection error. Please try again."
                case .InvalidMRZKey:
                    return "MRZ không hợp lệ."
                case .ResponseError(let description, let sw1, let sw2):
                    return "Sorry, there was a problem reading the passport. \(description) - (0x\(sw1), 0x\(sw2)"
                default:
                    return "Sorry, there was a problem reading the passport. Please try again"
            }
        case .successfulRead:
            return "Đọc thông tin thẻ thành công."
       }
    }
    
    func handleProgress(percentualProgress: Int) -> String {
        let p = (percentualProgress/20)
        let full = String(repeating: "🟢 ", count: p)
        let empty = String(repeating: "⚪️ ", count: 5-p)
        return "\(full)\(empty)"
    }
    
    func NFCSuccess() {
        
    }
    
    func NFCNotAvaiable() {
        let error = NSError()
        self.callbackFailure!("FAILED", "Error", error)
    }
}
