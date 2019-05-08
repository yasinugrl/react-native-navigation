#import "RNNEventEmitter.h"
#import "UIView+Utils.h"
#import <React/RCTFabricSurfaceHostingProxyRootView.h>
#import <React/RCTRootViewDelegate.h>
#import <React/RCTUIManager.h>

#define ComponentTypeScreen @"Component"
#define ComponentTypeTitle @"TopBarTitle"
#define ComponentTypeButton @"TopBarButton"
#define ComponentTypeBackground @"TopBarBackground"

typedef void (^RNNReactViewReadyCompletionBlock)(void);

@interface RNNReactView
    : RCTFabricSurfaceHostingProxyRootView <RCTRootViewDelegate>

- (instancetype)initWithBridge:(RCTBridge *)bridge
                    moduleName:(NSString *)moduleName
             initialProperties:(NSDictionary *)initialProperties
                  eventEmitter:(RNNEventEmitter *)eventEmitter
           reactViewReadyBlock:
               (RNNReactViewReadyCompletionBlock)reactViewReadyBlock;

@property(nonatomic, copy) RNNReactViewReadyCompletionBlock reactViewReadyBlock;
@property(nonatomic, strong) RNNEventEmitter *eventEmitter;

- (NSString *)componentId;

- (NSString *)componentType;

- (void)componentDidAppear;

- (void)componentDidDisappear;

- (void)invalidate;

@end
