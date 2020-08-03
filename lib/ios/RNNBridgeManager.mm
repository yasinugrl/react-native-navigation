#import "RNNBridgeManager.h"

#import <React/RCTBridge.h>
#import <React/RCTUIManager.h>

#ifdef RN_FABRIC_ENABLED
#import <React/RCTSurfacePresenter.h>
#endif

#import "RNNSplashScreen.h"
#import "RNNBridgeModule.h"
#import "RNNComponentViewCreator.h"
#import "RNNReactRootViewCreator.h"
#import "RNNReactComponentRegistry.h"
#import "RNNTurboModule.h"

@interface RNNBridgeManager() {
#ifdef RN_FABRIC_ENABLED
  RCTSurfacePresenter *_surfacePresenter;
#endif
}

@property (nonatomic, strong, readwrite) RCTBridge *bridge;
@property (nonatomic, strong, readwrite) RNNExternalComponentStore *store;
@property (nonatomic, strong, readwrite) RNNReactComponentRegistry *componentRegistry;

@end

@implementation RNNBridgeManager {
	NSDictionary* _launchOptions;
	id<RCTBridgeDelegate> _delegate;
	RCTBridge* _bridge;
	UIWindow* _mainWindow;
	
	RNNExternalComponentStore* _store;

	RNNCommandsHandler* _commandsHandler;
}

- (instancetype)initWithlaunchOptions:(NSDictionary *)launchOptions andBridgeDelegate:(id<RCTBridgeDelegate>)delegate mainWindow:(UIWindow *)mainWindow {
	if (self = [super init]) {
		_mainWindow = mainWindow;
		_launchOptions = launchOptions;
		_delegate = delegate;
		
		_store = [RNNExternalComponentStore new];
	}
	return self;
}

- (void)initializeBridge {
    _bridge = [[RCTBridge alloc] initWithDelegate:_delegate launchOptions:_launchOptions];
            
    #ifdef RN_FABRIC_ENABLED
            _surfacePresenter = [[RCTSurfacePresenter alloc] initWithBridge:_bridge config:nil];
            _bridge.surfacePresenter = _surfacePresenter;
    #endif
}

- (RNNTurboModule *)createTurboModule {
    return [[RNNTurboModule alloc] initWithBridge:_bridge mainWindow:_mainWindow nativeComponentStore:_store];
}

- (void)registerExternalComponent:(NSString *)name callback:(RNNExternalViewCreator)callback {
	[_store registerExternalComponent:name callback:callback];
}

- (NSArray<id<RCTBridgeModule>> *)extraModulesForBridge:(RCTBridge *)bridge {
	_eventEmitter = [[RNNEventEmitter alloc] init];
	return @[_eventEmitter];
}

@end

