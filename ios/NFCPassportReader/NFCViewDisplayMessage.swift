//
//  NFCViewDisplayMessage.swift
//  NFCPassportReader
//
//  Created by Andy Qua on 09/02/2021.
//

import Foundation

@available(iOS 13, macOS 10.15, *)
public enum NFCViewDisplayMessage {
    case requestPresentPassport
    case authenticatingWithPassport(Int)
    case readingDataGroupProgress(DataGroupId, Int)
    case error(NFCPassportReaderError)
    case successfulRead
}

@available(iOS 13, macOS 10.15, *)
extension NFCViewDisplayMessage {
    public var description: String {
        switch self {
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
                    case NFCPassportReaderError.TagNotValid:
                        return "Tag not valid."
                    case NFCPassportReaderError.MoreThanOneTagFound:
                        return "More than 1 tags was found. Please present only 1 tag."
                    case NFCPassportReaderError.ConnectionError:
                        return "Connection error. Please try again."
                    case NFCPassportReaderError.InvalidMRZKey:
                        return "MRZ không hợp lệ."
                    case NFCPassportReaderError.ResponseError(let description, let sw1, let sw2):
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
}
