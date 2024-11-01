import SwiftUI
import SwiftUIComponents
import TvManiac

struct HomeChildView: View {
    let screen: HomeComponentChild
    let bottomTabActions: [BottomTabAction]
    
    var body: some View {
        GeometryReader { geometry in
            ZStack(alignment: .bottom) {
                
                VStack {
                    switch onEnum(of: screen) {
                        case .discover(let screen):
                            DiscoverView(component: screen.component)
                        case .search(let screen):
                            SearchView(component: screen.component)
                        case .library(let screen):
                            LibraryView(component: screen.component)
                        case .settings(let screen):
                            SettingsView(component: screen.component)
                    }
                }
                .bottomTabSafeArea()
                .frame(width: geometry.size.width, height: geometry.size.height)

                BottomNavigation(actions: bottomTabActions)
            }
        }
        .background(Color.background)
        .edgesIgnoringSafeArea(.bottom)
    }
}

struct BottomTabSafeArea: ViewModifier {
    let height: CGFloat
    
    func body(content: Content) -> some View {
        content.safeAreaInset(edge: .bottom) {
            Color.clear.frame(height: height)
        }
    }
}

extension View {
    func bottomTabSafeArea(height: CGFloat = 74) -> some View {
        self.modifier(BottomTabSafeArea(height: height))
    }
}

