#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import "RNNScreenTransition.h"
#import "TransitionDelegate.h"

@interface StackTransitionManager : TransitionDelegate

- (instancetype)initWithScreenTransition:(RNNScreenTransition *)screenTransition bridge:(RCTBridge *)bridge fromVC:(UIViewController *)fromVC toVC:(UIViewController *)toVC;

- (BOOL)isValid;

@end
