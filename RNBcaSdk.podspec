
Pod::Spec.new do |s|
  s.name         = "RNBcaSdk"
  s.version      = "1.0.0"
  s.summary      = "RNBcaSdk"
  s.description  = "RNBcaSdk"
  s.homepage     = "https://github.com/author/RNBcaSdk"
  s.license      = "MIT"
  # s.license      = { :type => "MIT", :file => "FILE_LICENSE" }
  s.author             = { "author" => "author@domain.cn" }
  s.platform     = :ios, "13.0"
  s.source       = { :git => "https://github.com/author/RNBcaSdk.git", :tag => "master" }

  s.preserve_paths = 'LICENSE', 'README.md', 'package.json', 'index.js'
  s.source_files = "ios/**/*.{h,m,swift}"  
  s.requires_arc = true
  s.swift_version = "5.0"

  # add dependency below
  s.dependency "React"
  s.dependency "React-Core"
  s.dependency "QKMRZScanner"
  s.dependency "OpenSSL-Universal", '1.1.180'

  # set config
  s.xcconfig          = { 'OTHER_LDFLAGS' => '-weak_framework CryptoKit -weak_framework CoreNFC -weak_framework CryptoTokenKit' }

  s.pod_target_xcconfig = {
    'EXCLUDED_ARCHS[sdk=iphonesimulator*]' => 'arm64'
  }
  s.user_target_xcconfig = { 'EXCLUDED_ARCHS[sdk=iphonesimulator*]' => 'arm64' }

  # subspec here ...
  s.subspec 'NFCPassportReader' do |sb|
    sb.name             = "NFCPassportReader"
    sb.source_files     = "ios/NFCPassportReader/*.{h,m,swift}", "ios/NFCPassportReader/DataGroups/*.{swift}"
    sb.requires_arc     = true
  end

end

  