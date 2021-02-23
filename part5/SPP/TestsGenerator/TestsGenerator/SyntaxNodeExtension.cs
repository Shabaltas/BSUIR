using Microsoft.CodeAnalysis;

namespace TestsGenerator
{
    static class SyntaxNodeExtension
    {
        public static T GetParentSyntax<T>(this SyntaxNode syntaxNode)
            where T : SyntaxNode
        {
            syntaxNode = syntaxNode.Parent;
            return syntaxNode.GetType() == typeof(T) ? syntaxNode as T : GetParentSyntax<T>(syntaxNode);
        }
    }
}
