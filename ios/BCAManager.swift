//
//  RNBCAManager.swift
//  RNBcaSdk
//
//  Created by Khanh Khau - Ban Vien on 16/12/2022.
//  Copyright © 2022 Facebook. All rights reserved.
//

import Foundation
import QKMRZParser
import SwiftyTesseract

@available(iOS 15, *)
@objc(BCAManager)
class BCAManager: NSObject {
  
    var bridge: RCTBridge!
    
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
                     "faceImageBase64": imageData.base64EncodedString(options: .lineLength64Characters),
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
    
}
