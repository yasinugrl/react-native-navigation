#import <Foundation/Foundation.h>
#import <React/RCTUIManager.h>
#import <React/RCTUIManagerUtils.h>
#import <React/RCTUIManagerObserverCoordinator.h>

@interface TransitionDelegate : NSObject <UIViewControllerTransitioningDelegate, UIViewControllerAnimatedTransitioning, RCTUIManagerObserver>

- (instancetype)initWithBridge:(RCTBridge *)bridge fromVC:(UIViewController *)fromVC toVC:(UIViewController *)toVC;

- (NSArray *)prepareTransitionsFromVC:(UIViewController *)fromVC toVC:(UIViewController *)toVC containerView:(UIView *)containerView;

@end
