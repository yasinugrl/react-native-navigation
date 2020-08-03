require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

folly_flags = '-DFOLLY_NO_CONFIG -DFOLLY_MOBILE=1 -DFOLLY_USE_LIBCPP=1'
folly_compiler_flags = folly_flags + ' ' + '-Wno-comma -Wno-shorten-64-to-32'
folly_version = '2020.01.13.00'
boost_compiler_flags = '-Wno-documentation'

Pod::Spec.new do |s|
  s.name         = "ReactNativeNavigation"
  s.version      = package['version']
  s.summary      = package['description']

  s.authors      = "Wix.com"
  s.homepage     = package['homepage']
  s.license      = package['license']
  s.platform     = :ios, "9.0"

  s.module_name  = 'ReactNativeNavigation'
  s.default_subspec = 'Core'
  
  s.subspec 'Core' do |ss|
    s.source              = { :git => "https://github.com/wix/react-native-navigation.git", :tag => "#{s.version}" }
    s.source_files        = "lib/ios/**/*.{h,m,mm}","Common/cpp/**/*.cpp","Common/cpp/headers/**/*.h"
    s.exclude_files       = "lib/ios/ReactNativeNavigationTests/**/*.*", "lib/ios/OCMock/**/*.*"
  end
  
  s.subspec 'Fabric' do |ss|
    ss.compiler_flags      = "-DRN_FABRIC_ENABLED -DFOLLY_NO_CONFIG -DFOLLY_MOBILE=1 -DFOLLY_USE_LIBCPP=1 -Wno-comma -Wno-shorten-64-to-32"
    ss.pod_target_xcconfig = {
      "HEADER_SEARCH_PATHS" => "\"$(PODS_ROOT)/boost-for-react-native\" \"$(PODS_ROOT)/Folly\"",
      "CLANG_CXX_LANGUAGE_STANDARD" => "c++14" 
    }
    ss.dependency 'React-RCTFabric'
    ss.dependency 'React-Fabric'
    ss.dependency 'Folly/Fabric'
  end
  
  s.pod_target_xcconfig    = {
    "USE_HEADERMAP" => "YES",
    "HEADER_SEARCH_PATHS" => "\"$(PODS_TARGET_SRCROOT)/ReactCommon\" \"$(PODS_TARGET_SRCROOT)\" \"$(PODS_ROOT)/Folly\" \"$(PODS_ROOT)/boost-for-react-native\" \"$(PODS_ROOT)/DoubleConversion\" \"$(PODS_ROOT)/Headers/Private/React-Core\" "
  }
  s.compiler_flags = folly_compiler_flags + ' ' + boost_compiler_flags

  s.dependency 'Folly', folly_version

  s.dependency 'React'
  s.dependency 'React-RCTImage'
  s.dependency 'React-RCTText'
  s.frameworks = 'UIKit'
end
