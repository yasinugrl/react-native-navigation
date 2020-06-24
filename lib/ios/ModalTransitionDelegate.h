#import "TransitionDelegate.h"
#import "TransitionOptions.h"
#import "ContentTransitionCreator.h"

@interface ModalTransitionDelegate : TransitionDelegate

- (instancetype)initWithContentTransition:(TransitionOptions *)contentTransitionOptions bridge:(RCTBridge *)bridge fromVC:(UIViewController *)fromVC toVC:(UIViewController *)toVC;

@property (nonatomic, strong) TransitionOptions* contentTransitionOptions;

@end
